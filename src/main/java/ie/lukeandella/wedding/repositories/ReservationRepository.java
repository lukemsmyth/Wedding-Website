package ie.lukeandella.wedding.repositories;

import ie.lukeandella.wedding.pojos.Gift;
import ie.lukeandella.wedding.pojos.Reservation;
import ie.lukeandella.wedding.pojos.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT r FROM Reservation r WHERE r.gift = ?1 and r.user = ?2")
    Reservation findByGiftAndUser(Gift gift, User user);

    /*
        * See Spring Data JPA docs for how to use queries
        * https://docs.spring.io/spring-data/jpa/docs/current-SNAPSHOT/reference/html/#jpa.query-methods.at-query
     */

    /*
        * *************************************************
        * Returns a list of the current user's reservations
        * *************************************************
     */
    @Query
    List<Reservation> findReservationsByActiveEqualsAndUserEquals(boolean active, User currentUser);


//    @Query(value = "SELECT * FROM Reservation WHERE user_id = ?1 OR percentage <= 99", nativeQuery = true)
//    List<Reservation> getGiftsForDisplay(Long userId);
    //    @Query
//    List<Reservation> findReservationsByPercentageIsLessThanOrUserEquals(Integer percentage, User user);
//
//    @Query
//    List<Reservation> findReservationsByPercentageIsLessThan(Integer percentage);

}
