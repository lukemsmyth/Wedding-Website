package ie.lukeandella.wedding.repositories;

import ie.lukeandella.wedding.pojos.Gift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GiftRepository extends JpaRepository<Gift, Long> {

    @Query
    List<Gift> findGiftsByPercentageReservedIsLessThan(Integer p);

}
