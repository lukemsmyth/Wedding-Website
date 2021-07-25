package ie.lukeandella.wedding.controllers;

import ie.lukeandella.wedding.configuration.IAuthenticationFacade;
import ie.lukeandella.wedding.models.CustomUserDetails;
import ie.lukeandella.wedding.models.Gift;
import ie.lukeandella.wedding.models.ReservationRequest;
import ie.lukeandella.wedding.models.User;
import ie.lukeandella.wedding.services.GiftService;
import ie.lukeandella.wedding.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
public class GiftController {

//    public GiftController() {
//        super();
//    }

    @Autowired
    private final GiftService giftService;
    @Autowired
    private final UserService userService;
    @Autowired
    public GiftController(GiftService giftService, UserService userService){
        this.giftService = giftService;
        this.userService = userService;
    }

    /*
        * Actions for users to perform:
            * Browse
            * Reserve
     */

//    @PostMapping("/gifts/reserve")
//    public String saveBooks(@ModelAttribute ReservationRequest reservationRequest, Model model) {
//        final Authentication authentication = authenticationFacade.getAuthentication();
//        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
//        User user = customUserDetails.getUser();
//        giftService.reserveGifts(reservationRequest.getGifts(), user);
//        Set<Gift> gifts = userService.getGiftsOfUser(user.getId());
//        model.addAttribute("gifts", gifts);
//        model.addAttribute("user", user);
//        return "gift/gift-reserved";
//    }

    @GetMapping("/gifts")
    public String showGifts(Model model){
        model.addAttribute("gifts", giftService.getGifts());
        model.addAttribute("resreq", new ReservationRequest());
        return "gift/gifts";
    }

    @PostMapping("gifts/reserve/{id}")
    public String processReservation(@ModelAttribute("resreq") ReservationRequest resReq, @PathVariable("id") Long giftId, Model model){
        //First get the current User
        User user = userService.getCurrentUser();
        //Get gift object
        Gift gift = giftService.getGiftById(giftId);
        //Reserve the gift
        //Note: use the ReservationRequest object to pass the percentage reserved
        // and the PathVariable to pass the giftId
        giftService.reserveGift(user.getId(), gift.getId(), resReq.getPercentage());
        //Add model attributes
        model.addAttribute("user", user);
        model.addAttribute("gift", gift);
        System.out.println("GiftController.processReservation() line 74");
        return "gift/gift-reserved";
    }

//    @PostMapping("/gifts/reserve/{id}")
//    public String processReservation(@PathVariable("id") Long id, @ModelAttribute("gift") Gift gift, Model model){
//        //Get Authentication object via AuthenticationFacade
//        final Authentication authentication = authenticationFacade.getAuthentication();
//        //Get CustomUserDetailsObject using Authentication object
//        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
//        //Get User object using CustomUserDetails object
//        User user = customUserDetails.getUser();
//        Gift giftById = giftService.getGiftById(id);
//        //set the percentage reserved of the gift
////        gift.setPercentageReserved(x_gift.getPercentageReserved());
//        //Add the user object as a model attribute
//        model.addAttribute("user", user);
//        //Also add the gift...
//        model.addAttribute("gift", giftById);
//        //Reserve the gift
//        userService.reserveGift(user.getId(), id);  //passing user's id and gift's id
//        return "gift/gift-reserved";
//    }

    /*
        * Admin-only actions
            * Add a gift
            * Remove a gift
            * Edit a gift
     */

    //Take a new gift as form input
    @GetMapping("/gift/add")
    public String addGiftForm(Model model){
        model.addAttribute("gift", new Gift());
        return "gift/gift-add";
    }

    //Add the book to the database
    @PostMapping("/gift/added")
    public String submitForm(@ModelAttribute("gift") Gift gift){
        giftService.addGift(gift);
        return "gift/gift-added";
    }

}
