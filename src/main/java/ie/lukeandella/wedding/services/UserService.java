package ie.lukeandella.wedding.services;


import ie.lukeandella.wedding.configuration.IAuthenticationFacade;
import ie.lukeandella.wedding.exceptions.GiftNotExistsException;
import ie.lukeandella.wedding.exceptions.RoleNotExistsException;
import ie.lukeandella.wedding.exceptions.UserNotExistsException;
import ie.lukeandella.wedding.pojos.*;
import ie.lukeandella.wedding.repositories.GiftRepository;
import ie.lukeandella.wedding.repositories.ReservationRepository;
import ie.lukeandella.wedding.repositories.RoleRepository;
import ie.lukeandella.wedding.repositories.UserRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final GiftRepository giftRepository;

    @Autowired
    private final ReservationRepository reservationRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    public UserService(UserRepository userRepository, GiftRepository giftRepository, RoleRepository roleRepository, ReservationRepository reservationRepository){
        this.userRepository = userRepository;
        this.giftRepository = giftRepository;
        this.roleRepository = roleRepository;
        this.reservationRepository = reservationRepository;
    }

    public void register(User user, String siteURL) throws UnsupportedEncodingException, MessagingException {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        String randomCode = RandomString.make(64);
        user.setVerificationCode(randomCode);
        user.setEnabled(false);
        user.addRole(roleRepository.findByNameIs("MEMBER"));
        userRepository.save(user);
        sendVerificationEmail(user, siteURL);
    }

    private void sendVerificationEmail(User user, String siteURL)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "lukeandella2022@gmail.com";
        String senderName = "Luke & Ella";
        String subject = "Please verify your registration";
        String content = "Hello!<br>"
                + "<br>Thanks for registering :) <br>"
                + "Please click the link to verify your registration. <br>"
                + "<a href=\"[[URL]]\" target=\"_self\">VERIFY</a>"
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

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public List<User> getUsersExcCurrent(){
        return userRepository.findAllByIdIsNot(getCurrentUser().getId());
    }

    @Transactional
    public void deleteUser(Long userId){
        if(!userRepository.existsById(userId)){
            throw new IllegalStateException("user with id " + userId + " does not exist");
        }
        reservationRepository.deleteAllByUserIdEquals(userId);
        userRepository.deleteById(userId);
    }

    public User getById(Long userId) throws UserNotExistsException {
        return initUserObj(userId);
    }

    /*
     * "If you retrieve an entity, for example using the findOne method call
     * within a transactional method it has become managed from that point
     * by the persistence provider.Now if you make any changes to that entity
     * (which is actually a proxy object), upon transaction commit, those
     * changes will be persisted to the database, regardless of the fact of
     * invoking the save or update methods.save or persist has to be used when
     * you are creating a new entity from scratch and persistence provider
     * does not know of its existance yet.Remember that you can prevent making
     * any changes upon commit, if you use detach or evict methods on that
     * particular entity before those changes occur."
     *
     * https://stackoverflow.com/questions/46708063/springboot-jpa-need-no-save-on-transactional
     */

    public User getCurrentUser(){
        //Get Authentication object via AuthenticationFacade
        final Authentication authentication = authenticationFacade.getAuthentication();
        //Get CustomUserDetailsObject using Authentication object
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        //Get User object using CustomUserDetails object
        return customUserDetails.getUser();
    }

    public void addNewUser(NewUser newUser) throws RoleNotExistsException {
        User user = new User();
        user.setName(newUser.getEmail());
        user.setEmail(newUser.getEmail());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(newUser.getPassword());
        user.setPassword(password);
        user.setEnabled(true);
        Role adminRole = initRoleObj(roleRepository.findByNameIs("ADMIN").getId());
        Role memberRole = initRoleObj(roleRepository.findByNameIs("MEMBER").getId());
        if(newUser.getRole().equals("ADMIN")){
            user.addRole(adminRole);        //admin will also be member
        }
        user.addRole(memberRole);
        userRepository.save(user);
    }

    @Transactional
    public User updateUserInfo(Long id, String email, String password) throws UserNotExistsException {
        User user = initUserObj(id);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //Only set the fields which have been modified by the admin
        if(!email.isEmpty()){
            user.setName(email);
            user.setEmail(email);
        }else{
            user.setName(user.getName());
        }
        if(!password.isEmpty()){
            user.setPassword(passwordEncoder.encode(password));
        }else{
            user.setPassword(user.getPassword());
        }
        return user;
    }

    @Transactional
    public void toggleUserAdminStatus(Long id) throws UserNotExistsException, RoleNotExistsException {
        User user = initUserObj(id);
        if(user.hasRole("ADMIN")){
            user.getRoles().remove(user.getRoleByName("ADMIN"));
        }else{
            Role adminRole = initRoleObj(roleRepository.findByNameIs("ADMIN").getId());
            user.addRole(adminRole);
        }
    }


    //Helper method - throws exception if user cannot be found by id.
    public User initUserObj(Long userId) throws UserNotExistsException {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotExistsException("User with ID: " + userId + " does not exist."));
    }

    public Gift initGiftObj(Long giftId) throws GiftNotExistsException {
        return giftRepository.findById(giftId)
                .orElseThrow(() ->
                        new GiftNotExistsException("User with ID: " + giftId + " does not exist."));
    }

    public Role initRoleObj(Long id) throws RoleNotExistsException {
        return roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotExistsException("Role with ID: " + id + " does not exist."));
    }

    public User findByUsername(String username) {
        return userRepository.findByName(username);
    }

}
