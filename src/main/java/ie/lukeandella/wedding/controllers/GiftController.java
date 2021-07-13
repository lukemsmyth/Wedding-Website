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
        return "gift/gifts";
    }

    @GetMapping("/reserve")
    public String reserve(Model model){
        model.addAttribute("gift", new Gift());
        return "gift-reserved";
    }

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
