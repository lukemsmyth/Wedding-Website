package ie.lukeandella.wedding.controllers;

import ie.lukeandella.wedding.exceptions.GiftNotExistsException;
import ie.lukeandella.wedding.exceptions.RoleNotExistsException;
import ie.lukeandella.wedding.exceptions.UserNotExistsException;
import ie.lukeandella.wedding.pojos.*;
import ie.lukeandella.wedding.services.GiftService;
import ie.lukeandella.wedding.services.RoleService;
import ie.lukeandella.wedding.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

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
    private final RoleService roleservice;

//    @Autowired
//    RestResponseEntityExceptionHandler exceptionHandler;

    public GiftController
            (
            GiftService giftService,
            UserService userService,
            RoleService roleservice
            )
    {
        this.giftService = giftService;
        this.userService = userService;
        this.roleservice = roleservice;
    }

    /*
        * Actions for users to perform:
            * Browse
            * Reserve
     */

    @GetMapping("/gifts")
    public String showGifts(Model model){
        //Get current
        User currentUser = userService.getCurrentUser();
        model.addAttribute("current_user", currentUser);
        //Get roles
        Role adminRole = null;
        try {
            adminRole = roleservice.getRoleByName("ADMIN");
        } catch (RoleNotExistsException e) {
            e.getMessage();
        }
        model.addAttribute("admin_role", adminRole);
        Role userRole = null;
        try {
            userRole = roleservice.getRoleByName("USER");
        } catch (RoleNotExistsException e) {
            e.printStackTrace();
        }
        Role visitorRole = null;
        try {
            userRole = roleservice.getRoleByName("VISITOR");
        } catch (RoleNotExistsException e) {
            e.printStackTrace();
        }
        model.addAttribute("visitor_role", userRole);
        //Get gifts for display
        List<GiftForDisplay> giftsForDisplay = giftService.getGiftsForDisplay(currentUser);
        model.addAttribute("gifts", giftsForDisplay);
        //Get Percentage object
        model.addAttribute("percentage", new Percentage());
        //Get Gift object for adding/updating
        model.addAttribute("gift_to_update_or_add", new Gift());
        return "gift/gifts";
    }

    @PostMapping("/gifts/reserve/{id}")
    public String processReservation(@ModelAttribute("percentage") Percentage percentage, @PathVariable("id") Long giftId, Model model){
        try{
            //First get the current User
            User user = userService.getCurrentUser();
            //Get gift object
            Gift gift = giftService.getGiftById(giftId);
            //Reserve the gift
            //Note:
            //      Use the ReservationRequest object to pass the percentage
            //      reserved and the PathVariable to pass the giftId
            giftService.reserveGift(user.getId(), gift.getId(), percentage.getP());
            //Add model attributes
            model.addAttribute("user", user);
            model.addAttribute("gift", gift);
        }catch(GiftNotExistsException | UserNotExistsException ex){
            ex.printStackTrace();
            return "gift/gift-not-exists";
        }
        return "gift/gift-reserved";
    }

    @PostMapping("/gifts/cancel-reservation/{id}")
    public String cancelReservation(@ModelAttribute("percentage") Percentage percentage, @PathVariable("id") Long giftId, Model model){
        try{
            //First get the current User
            User currentUser = userService.getCurrentUser();
            Gift gift = giftService.getGiftById(giftId);
            giftService.cancelReservation(currentUser.getId(), giftId);
            model.addAttribute("current_user", currentUser);
            model.addAttribute("gift", gift);
        } catch (UserNotExistsException | GiftNotExistsException e) {
            e.printStackTrace();
            return "gift/gift-not-exists";
        }

        return "gift/reservation-cancelled";
    }

    /*
        * Admin-only actions
            * Add a gift
            * Remove a gift
            * Edit a gift
        * Note that all of these actions are performed from the gifts page.
     */

    /*
        * Add a gift to the database
     */
    @PostMapping("/gifts/add")
    public String submitForm(@ModelAttribute("gift") Gift gift){
        giftService.addGift(gift);
        return "gift/gift-added";
    }

    /*
        * Delete a gift form the database
     */
    @DeleteMapping("/gifts/delete/{id}")
    public String deleteGift(@PathVariable("id") Long giftId, Model model){
        //Get gift object
        Gift gift = null;
        try {
            gift = giftService.getGiftById(giftId);
        } catch (GiftNotExistsException e) {
            e.printStackTrace();
            return "gift/gift-not-exists";
        }
        giftService.deleteGift(giftId);
        //Add model attributes to confirm that gift was deletedHye
        model.addAttribute("gift", gift);
        return "gift/gift-deleted";
    }

    /*
        * Update a gift info
        * NOTE: this method does not change reservation status of gift
     */
//    @PostMapping("/gifts/update")
//    public String updateGiftInfo(@ModelAttribute("gift_to_update") Gift giftToUpdate, Model model){
//        //Update the gift object with fields contributed by the admin in the giftToUpdate object
//        giftService.updateGiftInfo(giftToUpdate.getId(), giftToUpdate.getName(), giftToUpdate.getDescription(), giftToUpdate.getPrice(), giftToUpdate.getLink());
//        model.addAttribute("gift", giftService.getGiftById(giftToUpdate.getId()));
//        return "gift/gift-updated";
//    }

    @PostMapping("/gifts/update")
    public String updateGiftInfo(@ModelAttribute("gift_to_update") Gift giftToUpdate, Model model){
        try {
            giftService.updateGiftInfo(giftToUpdate.getId(), giftToUpdate.getName(), giftToUpdate.getDescription(), giftToUpdate.getPrice(), giftToUpdate.getLink());
            //Update the gift object with fields contributed by the admin in the giftToUpdate object
            model.addAttribute("gift", giftService.getGiftById(giftToUpdate.getId()));
        } catch (GiftNotExistsException e) {
            e.printStackTrace();
            return "gift/gift-not-exists";
        }
        return "gift/gift-updated";
    }


}
