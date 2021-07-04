package ie.lukeandella.wedding.services;


import ie.lukeandella.wedding.models.Gift;
import ie.lukeandella.wedding.models.User;
import ie.lukeandella.wedding.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    //this can return a boolean to change what html is rendered for success/failure
    public void regUser(User user){
        userRepository.save(user);
    }

//    public boolean attemptUserRegistration(User user){
//        boolean result = true;
//        if(userRepository.existsByUsername(user.getUsername())) result = false;
//        if(user.getUsername().length() >= 20) result = false;
//        if(user.getPassword().length >= 20) result = false;
//        return result;
//    }

    public void deleteUser(Long userId){
        if(!userRepository.existsById(userId)){
            throw new IllegalStateException("user with id " + userId + " does not exist");
        }
        userRepository.deleteById(userId);
    }

    public Set<Gift> getGiftsOfUser(Long userId){
        User user = initUserObj(userId);
        return user.getGifts();
    }

    //Return a boolean to verify that the gift has been removed.
    @Transactional
    public boolean removeGiftFromUser(Long userId, Long giftId){
        User user = initUserObj(userId);
        Set<Gift> gifts = user.getGifts();
        for(Gift gift : gifts){
            if(gift.getId() == giftId){
                user.removeGift(gift);
                return true;
            }
        }
        return false;
    }

    @Transactional
    public void updateUserCredentials(Long userId, String username, String password){
        User user = initUserObj(userId);
        if(username != null) user.setUsername(username);
        if(password != null) user.setPassword(password);
    }

    //Helper method - throws exception if user cannot be found by id.
    public User initUserObj(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(
                        () -> new IllegalStateException
                                ("User with ID: " + userId + " does not exist.")
                );
        return user;
    }

}
