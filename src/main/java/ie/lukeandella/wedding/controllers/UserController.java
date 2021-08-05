package ie.lukeandella.wedding.controllers;

import ie.lukeandella.wedding.pojos.User;
import ie.lukeandella.wedding.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

//    //Landing page - options to login or register
//    @GetMapping("/")
//    public String landing(Model model){
//        return "landing/landing";
//    }
    @GetMapping("/")
    public String landing(Model model){
        model.addAttribute("user", new User());
        return "landing/landing2";
    }

    @GetMapping("/register")
    public String registerForm(Model model){
        model.addAttribute("user", new User());
        return "landing/registration/register";
    }

    @PostMapping("/process-registration")
    public String processRegistration(User user, HttpServletRequest request)
            throws UnsupportedEncodingException, MessagingException {
        user.setName(user.getEmail());
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

}
