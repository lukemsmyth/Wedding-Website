package ie.lukeandella.wedding.repositories;

import ie.lukeandella.wedding.pojos.Itinerary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItineraryRepository extends JpaRepository<Itinerary, Long> {

}
