package ie.lukeandella.wedding.services;


import ie.lukeandella.wedding.models.Gift;
import ie.lukeandella.wedding.models.User;
import ie.lukeandella.wedding.repositories.UserRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    public void register(User user, String siteURL) throws UnsupportedEncodingException, MessagingException {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        String randomCode = RandomString.make(64);
        user.setVerificationCode(randomCode);
        user.setEnabled(false);
        userRepository.save(user);
        sendVerificationEmail(user, siteURL);
    }

    private void sendVerificationEmail(User user, String siteURL)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "lukeandella2022@gmail.com";
        String senderName = "Luke & Ella";
        String subject = "Please verify your registration";
        String content = "Hi [[name]]!<br>"
                + "<br>Thanks for registering :) <br>"
                + "Please click the link to verify your registration. <br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thanks,<br>"
                + "Luke & Ella";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);
        content = content.replace("[[name]]", user.getName());
        String verifyURL = siteURL + "/verify?code=" + user.getVerificationCode();
        content = content.replace("[[URL]]", verifyURL);
        helper.setText(content, true);
        mailSender.send(message);
    }

    public boolean verify(String verificationCode) {
        User user = userRepository.findByVerificationCode(verificationCode);

        if(user == null || user.isEnabled()) return false;
        else {
            user.setVerificationCode(null);
            user.setEnabled(true);
            userRepository.save(user);
            return true;
        }
    }

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

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

    @Transactional
    public boolean reserveGift(User user, Gift gift){

        //Both sets have been intialised as HashSet in the model classes
        //so we can simply call .add() here.
        Set<User> reservees = gift.getReservees();
        reservees.add(user);
        Set<Gift> gifts = user.getGifts();
        gifts.add(gift);
        User xUser = userRepository.save(user);
        return user.equals(xUser);
    }

    //Return a boolean to verify that the gift has been removed.
    @Transactional
    public boolean removeGiftFromUser(Long userId, Long giftId){
        User user = initUserObj(userId);
        Set<Gift> gifts = user.getGifts();
        for(Gift gift : gifts){
            if(gift.getId().equals(giftId)){
                user.removeGift(gift);
                return true;
            }
        }
        return false;
    }

    @Transactional
    public void updateUserInfo(Long userId, String name, String email, String password){
        User user = initUserObj(userId);
        if(name != null) user.setName(name);
        if(email != null) user.setEmail(email);
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

    public User findByUsername(String username) {
        return userRepository.findByName(username);
    }
}
