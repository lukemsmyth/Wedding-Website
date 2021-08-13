package ie.lukeandella.wedding.controllers;

import ie.lukeandella.wedding.exceptions.ItineraryNotExistsException;
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

    @PostMapping("/new/itinerary")
    public String addItem(Model model, @ModelAttribute("item_to_add_or_update") Itinerary item){
        itineraryService.addItineraryItem(item);
        return "itinerary/item-added";
    }

    @PostMapping("/update/itinerary")
    public String updateItem(@ModelAttribute("item_to_add_or_update") Itinerary item, Model model){
        try{
            //update the object
            itineraryService.updateItineraryItem(item.getId(), item.getDateTime(), item.getLocation(), item.getTitle(), item.getDescription());
            Itinerary updatedItem = itineraryService.getById(item.getId());
            model.addAttribute("updated_item", updatedItem);
        } catch (ItineraryNotExistsException e) {
            e.printStackTrace();
            model.addAttribute("id", item.getId());
            return "itinerary/itinerary-not-exists";
        }
        return "itinerary/item-updated";
    }

    @PostMapping("/delete/itinerary/{id}")
    public String deleteItem(@PathVariable("id") Long id, Model model){
        try{
            model.addAttribute("item", itineraryService.getById(id));
            itineraryService.deleteItineraryItem(id);
        } catch (ItineraryNotExistsException e) {
            e.printStackTrace();
            model.addAttribute("id", id);
            return "itinerary/itinerary-not-exists";
        }
        return "itinerary/item-deleted";
    }

}
