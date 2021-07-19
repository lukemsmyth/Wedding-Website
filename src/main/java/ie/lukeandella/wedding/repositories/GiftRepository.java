package ie.lukeandella.wedding.repositories;

import ie.lukeandella.wedding.models.Gift;
import ie.lukeandella.wedding.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface GiftRepository extends JpaRepository<Gift, Long> {

    @Modifying
    @Query("update Gift g set g.reservees = ?1 where g.id = ?2")
    void setReservees(User user, Long giftId);
}
