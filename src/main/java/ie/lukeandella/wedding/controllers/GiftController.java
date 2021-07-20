package ie.lukeandella.wedding.controllers;

import ie.lukeandella.wedding.configuration.IAuthenticationFacade;
import ie.lukeandella.wedding.models.CustomUserDetails;
import ie.lukeandella.wedding.models.Gift;
import ie.lukeandella.wedding.models.User;
import ie.lukeandella.wedding.services.GiftService;
import ie.lukeandella.wedding.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class GiftController {

//    public GiftController() {
//        super();
//    }

    @Autowired
    private IAuthenticationFacade authenticationFacade;
    private final GiftService giftService;

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

    //Show all gifts
    @GetMapping("/gifts")
    public String listGifts(Model model){
        model.addAttribute("gifts", giftService.getGifts());
        return "gift/gifts";
    }

    @PostMapping("/gifts/reserve/{id}")
    public String processReservation(@PathVariable("id") Long id, Model model){
        //Get Authentication object via AuthenticationFacade
        final Authentication authentication = authenticationFacade.getAuthentication();
        //Get CustomUserDetailsObject using Authentication object
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        //Get User object using CustomUserDetails object
        User user = customUserDetails.getUser();
        System.out.println("Current user: " + user.getName());

        Gift gift = giftService.getGiftById(id);

        assert gift != null;
        System.out.println("Gift name: " + gift.getName());
//        Gift theGift = giftService.getGiftById(gift.getId());

        //Add the user object as a model attribute
        model.addAttribute("user", user);
        //Also add the gift...
        model.addAttribute("gift", gift);

        //Reserve the gift
        userService.reserveGift(user.getId(), id);  //passing user's id and gift's id
        return "gift/gift-reserved";
    }

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
