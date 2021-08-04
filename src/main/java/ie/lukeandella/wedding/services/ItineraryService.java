package ie.lukeandella.wedding.services;

import ie.lukeandella.wedding.pojos.Gift;
import ie.lukeandella.wedding.pojos.Itinerary;
import ie.lukeandella.wedding.repositories.ItineraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class ItineraryService {

    @Autowired
    private final ItineraryRepository itineraryRepository;

    @Autowired
    ItineraryService(ItineraryRepository itineraryRepository){
        this.itineraryRepository = itineraryRepository;
    }

    /*
        * List all itinerary items.
            * Itinerary implements Comparable interface so that natural ordering
            * of Itinerary objects is chronological from nearest to furthest.
     */
    public List<Itinerary> getItineraryItemsInOrder(){
        List<Itinerary> items = itineraryRepository.findAll();
        Collections.sort(items);
        return items;
    }

    /*
        * Add an itinerary item
     */
    public void addItineraryItem(Itinerary it){
        itineraryRepository.save(it);
    }

    /*
        * Delete an itinerary item
     */
    public void deleteItineraryItem(Long itemId){
        itineraryRepository.deleteById(itemId);
    }

    /*
        * Update an itinerary item
     */
    @Transactional
    public void updateItineraryItem(Long id, LocalDateTime dateTime, String location, String title, String description){
//        //YYYY-MM-DDThh:mm
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");
//        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, formatter);

//        if(!localDateTime.equals(it.getDateTime())) it.setDateTime(localDateTime);

        Itinerary it = initItineraryObj(id);
        String s = dateTime == null ? "null" : "not null";
        System.out.println(s);
        if(!dateTime.equals(it.getDateTime())) it.setDateTime(dateTime);
        if(!location.isEmpty()) it.setLocation(location);
        if(!title.isEmpty()) it.setTitle(title);
        if(!description.isEmpty()) it.setDescription(description);
        it.setLastUpdated(LocalDateTime.now());
    }

    public Itinerary getById(Long id){
        return initItineraryObj(id);
    }

    //helper method
    public Itinerary initItineraryObj(Long id){
        return itineraryRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("Itinerary item with ID: " + id + " does not exist.")
        );
    }

}
