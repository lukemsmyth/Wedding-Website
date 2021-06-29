package ie.lukeandella.wedding.controllers;

import ie.lukeandella.wedding.services.GiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GiftController {

    private final GiftService giftService;

    @Autowired
    public GiftController(GiftService giftService){
        this.giftService = giftService;
    }

    @GetMapping("/gifts")
    public String listGifts(Model model){
        model.addAttribute("gift_list", giftService.getGifts());
        return "gifts";
    }

}
