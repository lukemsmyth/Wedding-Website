package ie.lukeandella.wedding.services;

import ie.lukeandella.wedding.models.Gift;
import ie.lukeandella.wedding.models.User;
import ie.lukeandella.wedding.repositories.GiftRepository;
import ie.lukeandella.wedding.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
public class GiftService {

    @Autowired
    private final GiftRepository giftRepository;
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    public GiftService(GiftRepository giftRepository, UserRepository userRepository){
        this.giftRepository = giftRepository;
        this.userRepository = userRepository;
    }

    /*
        * Returns all instances of Gift. No need for a Menu class.
     */
    public List<Gift> getGifts(){
        return giftRepository.findAll();
    }

    public void addGift(Gift gift){
        giftRepository.save(gift);
    }

    /*
        * TO DO:
        * Custom exceptions.
     */
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
    public void reserveGifts(List<Gift> gifts, User user){
        //for each gift in ReservationRequest object, add reservee and set percentage reserved.
        for(Gift gift : gifts){
            Gift g = initGiftObj(gift.getId());
            g.setPercentageReserved(gift.getPercentageReserved() + g.getPercentageReserved());
            g.addReservee(initUserObj(user.getId()));
        }
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

//    @Transactional
//    public void toggleVisibility(Long giftId) {
//        Gift gift = initGiftObj(giftId);
//        gift.toggleVisibility();
//    }

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

    @Transactional
    public void reserveGift(Long userId, Long giftId, Integer percentage) {

        //Get Gift and User objects
        Gift gift = initGiftObj(giftId);
        User user = initUserObj(userId);

        //Update set of reservees
        Set<User> reservees = gift.getReservees();
        reservees.add(user);
        gift.setReservees(reservees);

        //Update percentage of gift reserved
        gift.setPercentageReserved(percentage);
    }
}
