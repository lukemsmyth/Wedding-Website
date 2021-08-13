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

    @Autowired
    private final GiftService giftService;

    @Autowired
    private final UserService userService;

    @Autowired
    private final RoleService roleservice;

    public GiftController(GiftService giftService,
                          UserService userService,
                          RoleService roleservice)
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

        User currentUser = userService.getCurrentUser();
        model.addAttribute("current_user", currentUser);

        try {
            Role adminRole = roleservice.getRoleByName("ADMIN");
            Role memberRole = roleservice.getRoleByName("MEMBER");
            Role visitorRole = roleservice.getRoleByName("VISITOR");
            model.addAttribute("admin_role", adminRole);
            model.addAttribute("member_role", memberRole);
            model.addAttribute("visitor_role", visitorRole);
        } catch (RoleNotExistsException e) {
            e.getMessage();
        }

        List<GiftForDisplay> giftsForDisplay = giftService.getGiftsForDisplay(currentUser);
        for(GiftForDisplay g : giftsForDisplay){
            System.out.println(g.toString());
        }
        model.addAttribute("giftsForDisplay", giftsForDisplay);
        model.addAttribute("percentage", new Percentage());
        model.addAttribute("gift", new Gift());
        return "gift/gifts";
    }

    @PostMapping("/reserve/{id}")
    public String processReservation(@ModelAttribute("percentage") Percentage percentage, @PathVariable("id") Long giftId, Model model)
    {
        try{
            User user = userService.getCurrentUser();
            Gift gift = giftService.getGiftById(giftId);
            giftService.reserveGift(user.getId(), gift.getId(), percentage.getP());
            model.addAttribute("user", user);
            model.addAttribute("gift", gift);
        } catch(UserNotExistsException ex){
            ex.printStackTrace();
            model.addAttribute("id", userService.getCurrentUser().getId());
            return "user/user-not-exists";
        }
        catch(GiftNotExistsException ex){
            ex.printStackTrace();
            model.addAttribute("id", giftId);
            return "gift/gift-not-exists";
        }
        return "gift/gift-reserved";
    }

    @PostMapping("/cancel-reservation/{id}")
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
    @PostMapping("/new/gift")
    public String addGift(@ModelAttribute("gift") Gift gift){
        giftService.addGift(gift);
        return "gift/gift-added";
    }

    /*
        * Delete a gift form the database
     */
    @DeleteMapping("/delete/{id}")
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
    @PostMapping("/update/gift")
    public String updateGiftInfo(@ModelAttribute("gift_to_update") Gift giftToUpdate, Model model){
        try {
            giftService.updateGiftInfo(giftToUpdate.getId(), giftToUpdate.getName(), giftToUpdate.getDescription(), giftToUpdate.getPrice(), giftToUpdate.getLink(), giftToUpdate.isSplitable());
            //Update the gift object with fields contributed by the admin in the giftToUpdate object
            model.addAttribute("gift", giftService.getGiftById(giftToUpdate.getId()));
        } catch (GiftNotExistsException e) {
            e.printStackTrace();
            model.addAttribute("id", giftToUpdate.getId());
            return "gift/gift-not-exists";
        }
        return "gift/gift-updated";
    }

}
