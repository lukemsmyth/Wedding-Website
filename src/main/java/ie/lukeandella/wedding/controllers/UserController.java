package ie.lukeandella.wedding.controllers;

import ie.lukeandella.wedding.models.User;
import ie.lukeandella.wedding.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

//    @GetMapping("/users")
//    public String listUsers(Model model){
//        model.addAttribute("user_list", userService.getUsers());
//        return "users";
//    }

    /*
    * REGISTERING A USER
     */

//    //First, take user info
//    @GetMapping("/user/register")
//    public String register(Model model){
//        model.addAttribute("user", new User());
//        return "user/register";
//    }

//    @PostMapping("/user/registering")
//    public String registering(@ModelAttribute("user") User user){
//        boolean added = userService.attemptUserRegistration(user);
//        if(added){
//            userService.regUser(user);
//            return "user/registered";
//        }else{
//            return "user/not-registered";
//        }
//    }


    /*
     *COME BACK TO THIS TO IMPLEMENT SOME FORM OF ATTEMPT TO DO BUSINESS LOGIC BEFORE
     * RETURNING SUCCESS MESSAGE.
     */
    //Then, display success message
//    @PostMapping("/user/registered")
//    public String registered(@ModelAttribute("user") User user){
//        userService.regUser(user);
//        return "user/registered";
//    }
}
