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
        * List all itinerary items in order
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
    public void updateItineraryItem(Long id, String dateTime, String location, String title, String description){
        //YYYY-MM-DDThh:mm
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, formatter);
        Itinerary it = initItineraryObj(id);
        if(!localDateTime.equals(it.getDateTime())) it.setDateTime(localDateTime);
        if(!location.isEmpty()) it.setLocation(location);
        if(!title.isEmpty()) it.setTitle(title);
        if(!description.isEmpty()) it.setDescription(description);
        it.setLastUpdated(LocalDateTime.now());
    }

    //helper method
    public Itinerary initItineraryObj(Long id){
        return itineraryRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("Itinerary item with ID: " + id + " does not exist.")
        );
    }

}
