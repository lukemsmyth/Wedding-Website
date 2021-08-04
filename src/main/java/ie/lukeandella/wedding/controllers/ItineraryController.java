package ie.lukeandella.wedding.controllers;

import ie.lukeandella.wedding.pojos.Gift;
import ie.lukeandella.wedding.pojos.Itinerary;
import ie.lukeandella.wedding.services.ItineraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

    @PostMapping("/itinerary/add")
    public String addItem(Model model, @ModelAttribute("item_to_add_or_update") Itinerary item){
        itineraryService.addItineraryItem(item);
        return "itinerary/item-added";
    }

    @PostMapping("/itinerary/update/{id}")
    public String updateItem(@PathVariable("id") Long id, @ModelAttribute("item_to_add_or_update") Itinerary item){
        //update the object
        System.out.println(item.getDescription());
        System.out.println(item.getDateTime().toString());
        itineraryService.updateItineraryItem(id, item.getDateTime(), item.getLocation(), item.getTitle(), item.getDescription());
        return "itinerary/item-updated";
    }

    @PostMapping("/itinerary/delete/{id}")
    public String deleteItem(@PathVariable("id") Long id, Model model){
        model.addAttribute("item", itineraryService.getById(id));
        itineraryService.deleteItineraryItem(id);
        return "itinerary/item-deleted";
    }

}
