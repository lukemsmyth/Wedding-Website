package ie.lukeandella.wedding.repositories;

import ie.lukeandella.wedding.models.Gift;
import ie.lukeandella.wedding.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface GiftRepository extends JpaRepository<Gift, Long> {

    @Query
    List<Gift> findGiftsByPercentageReservedIsLessThan(Integer p);



}
