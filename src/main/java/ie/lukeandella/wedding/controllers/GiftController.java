package ie.lukeandella.wedding.controllers;

import ie.lukeandella.wedding.configuration.IAuthenticationFacade;
import ie.lukeandella.wedding.models.CustomUserDetails;
import ie.lukeandella.wedding.models.Gift;
import ie.lukeandella.wedding.models.User;
import ie.lukeandella.wedding.services.GiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(method={RequestMethod.POST, RequestMethod.GET,RequestMethod.PUT, RequestMethod.DELETE})
public class GiftController {

//    public GiftController() {
//        super();
//    }

    @Autowired
    private IAuthenticationFacade authenticationFacade;
    private final GiftService giftService;

    @Autowired
    public GiftController(GiftService giftService){
        this.giftService = giftService;
    }

    /*
        * Actions for users to perform:
            * Browse
            * Reserve
     */

    //Show all gifts
    @GetMapping("/gifts")
    public String listGifts(Model model){
        model.addAttribute("gift_list", giftService.getGifts());
        return "gift/gifts";
    }

    @GetMapping("/gifts/reserve/confirm")
    public String confirmGiftReservation(@ModelAttribute Gift gift, Model model){
        model.addAttribute("gift", gift);   //in this way the gift object is passed from one controller to another
        return "gift/reserve/confirm";
    }

    @PostMapping("/gifts/reserve/confirmed")
    public String processReservation(@ModelAttribute Gift gift, Model model){
        //Get Authentication object via AuthenticationFacade
        final Authentication authentication = authenticationFacade.getAuthentication();
        //Get CustomUserDetailsObject using Authentication object
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        //Get User object using CustomUserDetails object
        User user = customUserDetails.getUser();
        //Add the user object as a model attribute
        model.addAttribute("user", user);
        //Also add gift object as a model attribute
        model.addAttribute("gift", gift);
        //Reserve the gift
        giftService.reserveGift(gift, user);   //should this be gift, user?
        return "gift/reserve/confirmed";
    }

//    //Reserve a gift
//    //This method gets the current user as a User object and adds it to a Model object as an attribute.
//    @PostMapping("/reserve")
//    public String test(@ModelAttribute Gift gift, Model model){
//        //Get Authentication object via AuthenticationFacade
//        final Authentication authentication = authenticationFacade.getAuthentication();
//        //Get CustomUserDetailsObject using Authentication object
//        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
//        //Get User object using CustomUserDetails object
//        model.addAttribute("user", customUserDetails.getUser());
//        model.addAttribute("gift", new Gift());
//        return "home/test-result";
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
