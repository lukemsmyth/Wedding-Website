package ie.lukeandella.wedding.controllers;

import ie.lukeandella.wedding.pojos.Itinerary;
import ie.lukeandella.wedding.services.ItineraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ItineraryController {

    @Autowired
    ItineraryService itineraryService;

    @Autowired
    public ItineraryController (ItineraryService itineraryService){
        this.itineraryService = itineraryService;
    }

    /*
        * Display all itinerary items
     */
    @GetMapping("/itinerary")
    public String itinerary(Model model){
        List<Itinerary> items = itineraryService.getItineraryItemsInOrder();
        model.addAttribute("items", items);
        model.addAttribute("item_to_add_or_update", new Itinerary());
        return "itinerary/itinerary";
    }

}
