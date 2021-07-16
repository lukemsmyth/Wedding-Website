package ie.lukeandella.wedding.services;

import ie.lukeandella.wedding.models.CustomUserDetails;
import ie.lukeandella.wedding.models.Gift;
import ie.lukeandella.wedding.models.User;
import ie.lukeandella.wedding.repositories.GiftRepository;
import ie.lukeandella.wedding.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@PersistenceContext(type = PersistenceContextType.EXTENDED)
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
    public void toggleVisibility(Long giftId) {
        Gift gift = initGiftObj(giftId);
        gift.toggleVisibility();
    }

    //helper method
    public Gift initGiftObj(Long giftId){
        return giftRepository.findById(giftId).orElseThrow(
                () -> new IllegalStateException("Gift with ID: " + giftId + " does not exist.")
                );
    }

//    @Transactional
    public void reserveGift(Gift gift, User user) {
        //Use custom user to find current user and get User object from repo
//        User user = userRepository.findByName(user.getUsername());
        user.addGift(gift);
        Set<User> userSet = new HashSet<>();
        userSet.add(user);
        gift.setReservees(userSet);
//        giftRepository.save(gift);
//        userRepository.save(user);
    }
}
