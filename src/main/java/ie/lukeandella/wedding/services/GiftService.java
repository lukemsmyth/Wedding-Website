package ie.lukeandella.wedding.services;

import ie.lukeandella.wedding.models.Gift;
import ie.lukeandella.wedding.repositories.GiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class GiftService {

    private final GiftRepository giftRepository;

    @Autowired
    public GiftService(GiftRepository giftRepository){
        this.giftRepository = giftRepository;
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

    public Gift initGiftObj(Long giftId){
        return giftRepository.findById(giftId).orElseThrow(
                () -> new IllegalStateException("Gift with ID: " + giftId + " does not exist.")
                );
    }
}
