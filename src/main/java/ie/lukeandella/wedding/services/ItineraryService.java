package ie.lukeandella.wedding.services;

import ie.lukeandella.wedding.exceptions.ItineraryNotExistsException;
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
        it.setLastUpdated(LocalDateTime.now());
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
        * Each field will submit a null value as default. Therefore, by
        * checking each field for null, we can update every field in one method.
     */
    @Transactional
    public void updateItineraryItem(Long id, LocalDateTime dateTime, String location, String title, String description) throws ItineraryNotExistsException {
        /*
         * Each time this method is called, every field is updated by Hibernate.
         * So, it is necessary to explicitly reset every value even if the admin
         * has provided null. Hence, the somewhat verbose if-else statements below.
         */
        Itinerary it = initItineraryObj(id);
        if(dateTime != null){
            it.setDateTime(dateTime);
        }else{
            it.setDateTime(it.getDateTime());
        }
        if(!location.isEmpty()){
            it.setLocation(location);
        }else{
            it.setLocation(it.getLocation());
        }
        if(!title.isEmpty()){
            it.setTitle(title);
        }else{
            it.setTitle(it.getTitle());
        }
        if(!description.isEmpty()){
            it.setDescription(description);
        }else{
            it.setTitle(it.getTitle());
        }
        it.setLastUpdated(LocalDateTime.now());
    }

    public Itinerary getById(Long id) throws ItineraryNotExistsException {
        return initItineraryObj(id);
    }

    //helper method
    public Itinerary initItineraryObj(Long id) throws ItineraryNotExistsException {
        return itineraryRepository.findById(id).orElseThrow(
                () -> new ItineraryNotExistsException("Itinerary item with ID: " + id + " does not exist.")
        );
    }

}
