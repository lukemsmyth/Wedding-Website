package ie.lukeandella.wedding.controllers;

import ie.lukeandella.wedding.models.Gift;
import ie.lukeandella.wedding.services.GiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class GiftController {

    private final GiftService giftService;

    @Autowired
    public GiftController(GiftService giftService){
        this.giftService = giftService;
    }

    //Show all gifts
    @GetMapping("/gifts")
    public String listGifts(Model model){
        model.addAttribute("gift_list", giftService.getGifts());
        return "gifts";
    }

    //Take a new gift as form input
    @GetMapping("/addgift")
    public String addGiftForm(Model model){
        model.addAttribute("gift", new Gift());
        return "addgiftform";
    }

    //Add the book to the database
    @PostMapping("/addgift")
    public String submitForm(@ModelAttribute("gift") Gift gift){
        giftService.addGift(gift);
        return "giftadded";
    }

}
