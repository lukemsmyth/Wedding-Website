package ie.lukeandella.wedding.controllers;

import ie.lukeandella.wedding.exceptions.GiftNotExistsException;
import ie.lukeandella.wedding.exceptions.RoleNotExistsException;
import ie.lukeandella.wedding.exceptions.UserNotExistsException;
import ie.lukeandella.wedding.exceptions.UsernameTakenException;
import ie.lukeandella.wedding.pojos.Gift;
import ie.lukeandella.wedding.pojos.NewUser;
import ie.lukeandella.wedding.pojos.User;
import ie.lukeandella.wedding.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/")
    public String landing(Model model){
        User visitor = userService.findByUsername("visitor@x.com");
        model.addAttribute("user", visitor);
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
        user.setName(user.getEmail());          //quick hack to make email the username to enforce unique usernames.
        try{
            userService.register(user, getSiteURL(request));
        }
        catch(UsernameTakenException e){
            return "landing/registration/username-taken";
        }

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

    @GetMapping("/invalid-credential")
    public String invalidCredential(){
        return "user/invalid-credential";
    }

    @GetMapping("/users")
    public String manageUsers(Model model){
        model.addAttribute("user_to_update", new User());
        model.addAttribute("new_user", new NewUser());
        //Do not show the current user to themselves so that they cannot
        //accidentally toggle their admin status to off and cause an exception.
        model.addAttribute("users", userService.getUsersExcCurrent());
        return "user/manage-users";
    }

    @PostMapping("/new/user")
    public String newUser(@ModelAttribute("new_user") NewUser newUser){
        try {
            userService.addNewUser(newUser);
        } catch (RoleNotExistsException | UsernameTakenException e) {
            e.printStackTrace();
            return "user/failed-to-add-user";
        }
        return "user/user-added";
    }

    @PostMapping("update/user")
    public String updateUser(@ModelAttribute("user_to_update") User user, Model model){
        try {
            User updatedUser = userService.updateUserInfo(user.getId(), user.getEmail(), user.getPassword());
            model.addAttribute("updatedUser", updatedUser);
        } catch (UserNotExistsException e) {
            e.printStackTrace();
            model.addAttribute("id", user.getId());
            return "user/user-not-exists";
        }
        return "user/user-updated";
    }

    @GetMapping("update/user-admin-status/{id}")
    public String toggleUserAdminStatus(@ModelAttribute("user_to_update") User user, @PathVariable("id") Long id, Model model){
        try {
            userService.toggleUserAdminStatus(id);
            model.addAttribute("user_admin_status_toggled", userService.getById(id));
        } catch (UserNotExistsException e) {
            e.printStackTrace();
            model.addAttribute("id", id);
            return "user/user-not-exists";
        } catch (RoleNotExistsException e) {
            e.printStackTrace();
            return "user/role-not-exists";
        }
        return "user/user-admin-status-toggled";
    }

    @GetMapping("/delete/user/{id}")
    public String deleteUser(@PathVariable("id") Long id, Model model){
        try {
            User user = userService.getById(id);
            userService.deleteUser(id);
            //Add model attributes to confirm that gift was deleted
            model.addAttribute("user", user);
        } catch (UserNotExistsException e) {
            e.printStackTrace();
            model.addAttribute(id);
            return "user/user-not-exists";
        } catch (GiftNotExistsException giftNotExistsException) {
            giftNotExistsException.printStackTrace();
            return "gift/gift-not-exists";
        }
        return "user/user-deleted";
    }



}
