package ie.lukeandella.wedding.controllers;

import ie.lukeandella.wedding.models.Gift;
import ie.lukeandella.wedding.models.User;
import ie.lukeandella.wedding.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/user")
    public String showUser(Model model){
        User user = userService.getById(3L);
        model.addAttribute("user", user);
        return "user";
    }

    @PostMapping("/update-user")
    public String updateUser(@ModelAttribute("user") User user){
        userService.updateUserInfo(3L, null, user.getEmail(), null);
        return "updated-user";
    }


}
