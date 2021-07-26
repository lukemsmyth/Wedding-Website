package ie.lukeandella.wedding.repositories;

import ie.lukeandella.wedding.models.Gift;
import ie.lukeandella.wedding.models.Reservation;
import ie.lukeandella.wedding.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT r FROM Reservation r WHERE r.gift = ?1 and r.user = ?2")
    Reservation findByGiftAndUser(Gift gift, User user);
}
