package ie.lukeandella.wedding.controllers;

import ie.lukeandella.wedding.pojos.*;
import ie.lukeandella.wedding.services.GiftService;
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
    public GiftController(GiftService giftService, UserService userService){
        this.giftService = giftService;
        this.userService = userService;
    }

    /*
        * Actions for users to perform:
            * Browse
            * Reserve
     */

    @GetMapping("/gifts")
    public String showGifts(Model model){
        User currentUser = userService.getCurrentUser();
        List<GiftForDisplay> giftsForDisplay = giftService.getGiftsForDisplay(currentUser);
        model.addAttribute("gifts", giftsForDisplay);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("percentage", new Percentage());
        model.addAttribute("gift_to_update_or_add", new Gift());       //for the admin to update gift object or add a gift to the db
        return "gift/gifts";
    }

    @PostMapping("/gifts/reserve/{id}")
    public String processReservation(@ModelAttribute("percentage") Percentage percentage, @PathVariable("id") Long giftId, Model model){
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
        return "gift/gift-reserved";
    }

    @PostMapping("/gifts/cancel-reservation/{id}")
    public String cancelReservation(@ModelAttribute("percentage") Percentage percentage, @PathVariable("id") Long giftId, Model model){
        //First get the current User
        User user = userService.getCurrentUser();
        Gift gift = giftService.getGiftById(giftId);
        giftService.cancelReservation(user.getId(), giftId);
        model.addAttribute("user", user);
        model.addAttribute("gift", gift);
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
        Gift gift = giftService.getGiftById(giftId);
        giftService.deleteGift(giftId);
        //Add model attributes to confirm that gift was deletedHye
        model.addAttribute("gift", gift);
        return "gift/gift-deleted";
    }

    /*
        * Update a gift info
        * NOTE: this method does not change reservation status of gift
     */
    @PostMapping("/gifts/update")
    public String updateGiftInfo(@ModelAttribute("gift_to_update") Gift giftToUpdate, Model model){
        //Update the gift object with fields contributed by the admin in the giftToUpdate object
        giftService.updateGiftInfo(giftToUpdate.getId(), giftToUpdate.getName(), giftToUpdate.getDescription(), giftToUpdate.getPrice(), giftToUpdate.getLink());
        model.addAttribute("gift", giftService.getGiftById(giftToUpdate.getId()));
        return "gift/gift-updated";
    }





}
