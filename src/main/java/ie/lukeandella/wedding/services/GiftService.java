package ie.lukeandella.wedding.services;

import ie.lukeandella.wedding.models.Gift;
import ie.lukeandella.wedding.models.Reservation;
import ie.lukeandella.wedding.models.User;
import ie.lukeandella.wedding.repositories.GiftRepository;
import ie.lukeandella.wedding.repositories.ReservationRepository;
import ie.lukeandella.wedding.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
public class GiftService {

    @Autowired
    private final GiftRepository giftRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final ReservationRepository reservationRepository;

    @Autowired
    public GiftService(GiftRepository giftRepository, UserRepository userRepository, ReservationRepository reservationRepository){
        this.giftRepository = giftRepository;
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
    }

    /*
        * ******************************
        * Returns all instances of Gift.
        * ******************************
     */
    public List<Gift> getGifts(){
        return giftRepository.findAll();
    }

    /*
        * **********************************************************************
        * Returns a list of gifts containing only unreserved gifts or gifts
        * reserved by the current user (to allow cancellation of reservation).
        * **********************************************************************
     */
    public List<Gift> getGiftsForDisplay(User currentUser){
        //Get all gifts
        List<Gift> gifts = giftRepository.findAll();
        //Remove gifts which are fully reserved and which
        //were not reserved in part or in whole by the
        //current user.
        int i = 0;
        while(i < gifts.size()){
            if(gifts.get(i).getPercentageReserved() == 100){
                Set<Reservation> reservations = gifts.get(i).getReservations();
                for(Reservation reservation : reservations){
                    if(!reservation.getUser().equals(currentUser)){
                        gifts.remove((gifts.get(i)));
                    }
                }
            }
            i++;
        }
        //Return redacted list
        return gifts;
    }

    /*
        * ***********************
        * Reserve a gift (in whole or in part)
        * ***********************
     */
    @Transactional
    public void reserveGift(Long userId, Long giftId, Integer percentage) {

        //Get Gift User objects
        try{
            Gift gift = initGiftObj(giftId);
            User user = initUserObj(giftId);
            //Instantiate new Reservation object
            Reservation reservation = new Reservation(percentage, gift, user);

            //Update Gift and User objects
            user.updateReservations(reservation);   //No need to persist already existing db objects bc of @Transactional
            gift.updateReservations(reservation);

            //Persist Reservation object
            reservationRepository.save(reservation);

            //Update percentage of gift reserved
            //This is, strictly speaking, redundant but it is helpful for determining
            //whether a gift will be rendered by Thymeleaf
            gift.setPercentageReserved(gift.getPercentageReserved() + percentage);
        }catch(IllegalStateException e){
            e.printStackTrace();
        }


    }

    /*
     * ***********************
     * Cancel a reservation
     * ***********************
     */
    @Transactional
    public void cancelReservation(Long userId, Long giftId) {
        try{
            //Get Gift User objects
            Gift gift = initGiftObj(giftId);
            User user = initUserObj(userId);

            //Get the associated reservation object
            Reservation reservation = reservationRepository.findByGiftAndUser(gift, user);

            //Remove reservation from gift and user
            gift.updateReservations(reservation);
            user.updateReservations(reservation);

            //Delete reservation
            reservationRepository.delete(reservation);
        }catch(IllegalStateException e){
            e.printStackTrace();
        }
    }

    public void addGift(Gift gift){
        giftRepository.save(gift);
    }

    public void deleteGift(Long giftId){
        if(!giftRepository.existsById(giftId)){
            throw new IllegalStateException("The gift with ID " + giftId + " does not exist");
        }
        giftRepository.deleteById(giftId);
    }

    //Remember that initGiftObj throws an exception!!
    public Gift getGiftById(Long giftId){
        return initGiftObj(giftId);
    }

    @Transactional
    public void updateGiftInfo(Long giftId, String name, String description, String image, Double price, String link){
        Gift gift = initGiftObj(giftId);
        //Only set the fields which have been modified by the admin
        if(name != null) gift.setName(name);
        if(description != null) gift.setDescription(description);
        if(image != null) gift.setImage(image);
        if(price != null) gift.setPrice(price);
        if(link != null) gift.setLink(link);
    }

    @Transactional
    public void setPercentageReserved(Long giftId, Integer percentage){
        Gift gift = initGiftObj(giftId);
        gift.setPercentageReserved(percentage);
    }

    //helper methods
    public Gift initGiftObj(Long giftId){
        return giftRepository.findById(giftId).orElseThrow(
                () -> new IllegalStateException("Gift with ID: " + giftId + " does not exist.")
                );
    }
    public User initUserObj(Long userId){
        return userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("User with ID: " + userId + " does not exist.")
        );
    }

}
