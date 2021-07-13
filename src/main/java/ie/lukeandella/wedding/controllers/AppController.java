package ie.lukeandella.wedding.controllers;

import ie.lukeandella.wedding.models.User;
import ie.lukeandella.wedding.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/*
    * This class controls the register/authenticate functionality.
 */
@Controller
public class AppController {

    @Autowired
    private UserService userService;

    //Landing page - options to login or register
    @GetMapping("/")
    public String landing(Model model){
        return "landing/landing";
    }

    //Show the registration form
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "landing/registration/register";
    }

    @PostMapping("/process-registration")
    public String processRegistration(User user, HttpServletRequest request)
            throws UnsupportedEncodingException, MessagingException {
        userService.register(user, getSiteURL(request));
        return "landing/registration/success";
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code) {
        if (userService.verify(code)) {
            return "landing/registration/verify/verify-success";
        } else {
            return "landing/registration/verify/verify-failure";
        }
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("user", new User());
        return "landing/login/login";
    }

    @GetMapping("/home")
    public String home(){
        return "home/home";
    }

    //FAQS - need to move this to FAQs controller
    @GetMapping("/faqs")
    public String faqs(){
        return "faqs/faqs";
    }

//    @GetMapping("/users")
//    public String listUsers(Model model) {
//        List<User> users = userService.getUsers();
////        List<User> listUsers = userRepo.findAll();
//        model.addAttribute("listUsers", users);
//        return "users";
//    }
}
