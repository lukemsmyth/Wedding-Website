package ie.lukeandella.wedding.services;

import ie.lukeandella.wedding.exceptions.GiftNotExistsException;
import ie.lukeandella.wedding.exceptions.UserNotExistsException;
import ie.lukeandella.wedding.pojos.Gift;
import ie.lukeandella.wedding.pojos.GiftForDisplay;
import ie.lukeandella.wedding.pojos.Reservation;
import ie.lukeandella.wedding.pojos.User;
import ie.lukeandella.wedding.repositories.GiftRepository;
import ie.lukeandella.wedding.repositories.ReservationRepository;
import ie.lukeandella.wedding.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class GiftService {

    @Autowired
    private final GiftRepository giftRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final ReservationRepository reservationRepository;

    @Autowired
    public GiftService(GiftRepository giftRepository, UserRepository userRepository, ReservationRepository reservationRepository) {
        this.giftRepository = giftRepository;
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
    }

    /*
     * ******************************
     * Returns all instances of Gift.
     * ******************************
     */
    public List<Gift> getGifts() {
        return giftRepository.findAll();
    }

    /*
     * **********************************************************************
     * Returns a list of gifts containing only unreserved gifts or gifts
     * reserved by the current user (to allow cancellation of reservation).
     * **********************************************************************
     */
//    public List<GiftForDisplay> getGiftsForDisplay(User currentUser) {
//        List<GiftForDisplay> giftsForDisplay = new ArrayList<>();
//        List<Gift> allGifts = giftRepository.findAll();
//        for (Gift g : allGifts) {
//            //if wholly reserved and user has reserved some part
//            if (g.getPercentageReserved() == 100 && g.userHasReserved(currentUser)) {
//                giftsForDisplay.add(new GiftForDisplay(g, false, true));
//            }
//            //if wholly reserved AND user has not reserved any part AND user is admin
//            else if (g.getPercentageReserved() == 100 && !g.userHasReserved(currentUser) && currentUser.hasRole("ADMIN")) {
//                giftsForDisplay.add(new GiftForDisplay(g, false, false));
//            }
//            //if NOT wholly reserved AND user has reserved some part
//            else if (g.getPercentageReserved() < 100 && g.userHasReserved(currentUser)) {
//                giftsForDisplay.add(new GiftForDisplay(g, false, true));
//            }
//            //if NOT wholly reserved AND user has NOT reserved any part
//            else {
//                giftsForDisplay.add(new GiftForDisplay(g, true, false));
//            }
//        }
//        return giftsForDisplay;
//    }

    public List<GiftForDisplay> getGiftsForDisplay(User currentUser) {
        List<Gift> allGifts = giftRepository.findAll();
        if(currentUser.hasRole("ADMIN")){
            return getGiftsForDisplayToAdmin(allGifts);
        }
        return getGiftsForDisplayToMember(allGifts, getGiftsOfCurrentUser(currentUser));
    }

    public List<GiftForDisplay> getGiftsForDisplayToAdmin(List<Gift> gifts){
        List<GiftForDisplay> giftsForDisplay = new ArrayList<>();
        System.out.println("Current user is admin...");
        for(Gift g : gifts){
            giftsForDisplay.add(new GiftForDisplay(g, false, false));
        }
        return giftsForDisplay;
    }

    public List<Gift> getGiftsOfCurrentUser(User currentUser){
        List<Reservation> usersReservations = reservationRepository.findReservationsByUserEquals(currentUser);
        List<Gift> giftsOfCurrentUser = new ArrayList<>();
        for(Reservation reservation : usersReservations){
            if(!giftsOfCurrentUser.contains(reservation.getGift())){
                giftsOfCurrentUser.add(reservation.getGift());
            }
        }
        return giftsOfCurrentUser;
    }

    public List<GiftForDisplay> getGiftsForDisplayToMember(List<Gift> gifts, List<Gift> giftsOfCurrentUser){
        List<GiftForDisplay> giftsForDisplay = new ArrayList<>();
        for (Gift g : gifts) {
            //If user has reserved part or all of gift
            if (giftsOfCurrentUser.contains(g)) {
                giftsForDisplay.add(new GiftForDisplay(g, false, true));
            }
            else{
                if(g.getPercentageReserved() < 100){
                    giftsForDisplay.add(new GiftForDisplay(g, true, false));
                }

            }
        }
        return giftsForDisplay;
    }
        /*
         * ************************************
         * Reserve a gift (in whole or in part)
         * ************************************
         */
        @Transactional
        public void reserveGift (Long userId, Long giftId, Integer percentage) throws
        GiftNotExistsException, UserNotExistsException {
            //Get Gift User objects
            Gift gift = initGiftObj(giftId);
            User user = initUserObj(userId);
            if (percentage == 0) percentage = 100;           //Quick fix
            //Instantiate new Reservation object
            Reservation reservation = new Reservation(percentage, gift, user);
            //Update Gift and User objects
            user.updateReservations(reservation);
            gift.updateReservations(reservation);
            reservationRepository.save(reservation);
            //Update percentage of gift reserved
            gift.setPercentageReserved(gift.getPercentageReserved() + percentage);

        }

        /*
         * ***********************
         * Cancel a reservation
         * ***********************
         */
        @Transactional
        public void cancelReservation (Long userId, Long giftId) throws GiftNotExistsException, UserNotExistsException {
            //Get Gift User objects
            Gift gift = initGiftObj(giftId);
            User user = initUserObj(userId);
            //Get the associated reservation object and set active to false
            Reservation res = reservationRepository.findByGiftAndUser(gift, user);
            reservationRepository.deleteById(res.getId());
            //Update gift's percentage reserved
            gift.setPercentageReserved(gift.getPercentageReserved() - res.getPercentage());
        }

        public void addGift (Gift gift){
            gift.setPercentageReserved(0);
            giftRepository.save(gift);
        }

        @Transactional
        public void deleteGift (Long giftId){
            if (!giftRepository.existsById(giftId)) {
                throw new IllegalStateException("The gift with ID " + giftId + " does not exist");
            }
            reservationRepository.deleteAllByGiftIdEquals(giftId);
            giftRepository.deleteById(giftId);
        }

        public Gift getGiftById (Long giftId) throws GiftNotExistsException {
            return initGiftObj(giftId);
        }

        @Transactional
        public void updateGiftInfo (Long giftId, String name, String description, Double price, String link,boolean splitable)
        throws GiftNotExistsException {
            Gift gift = initGiftObj(giftId);
            //Only set the fields which have been modified by the admin
            if (!name.isEmpty()) {
                gift.setName(name);
            } else {
                gift.setName(gift.getName());
            }
            if (!description.isEmpty()) {
                gift.setDescription(description);
            } else {
                gift.setDescription(gift.getDescription());
            }
            if (price != null) {
                gift.setPrice(price);
            } else {
                gift.setPrice(gift.getPrice());
            }
            if (!link.isEmpty()) {
                gift.setLink(link);
            } else {
                gift.setLink(gift.getLink());
            }
            gift.setSplitable(splitable);
        }

        @Transactional
        public void setPercentageReserved (Long giftId, Integer percentage) throws GiftNotExistsException {
            Gift gift = initGiftObj(giftId);
            gift.setPercentageReserved(percentage);
        }

        //helper methods

        public Gift initGiftObj (Long giftId) throws GiftNotExistsException {
            return giftRepository.findById(giftId).orElseThrow(
                    () -> new GiftNotExistsException("Gift with ID: " + giftId + " does not exist.")
            );
        }
        public User initUserObj (Long userId) throws UserNotExistsException {
            return userRepository.findById(userId).orElseThrow(
                    () -> new UserNotExistsException("User with ID: " + userId + " does not exist.")
            );
        }

    }
