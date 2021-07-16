package ie.lukeandella.wedding.controllers;

import ie.lukeandella.wedding.configuration.IAuthenticationFacade;
import ie.lukeandella.wedding.models.CustomUserDetails;
import ie.lukeandella.wedding.models.User;
import ie.lukeandella.wedding.repositories.UserRepository;
import ie.lukeandella.wedding.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GetUserWithCustomInterfaceController {

    @GetMapping("/test")
    public String test(){
        return "home/test";
    }

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    public GetUserWithCustomInterfaceController() {
        super();
    }

    //This method gets the current user as a User object and adds it to a Model object as an attribute.
    @GetMapping("/test/username")
    public String test(Model model){
        //Get Authentication object via AuthenticationFacade
        final Authentication authentication = authenticationFacade.getAuthentication();
        //Get CustomUserDetailsObject using Authentication object
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        //Get User object using CustomUserDetails object
        model.addAttribute("user", customUserDetails.getUser());
        return "home/test-result";
    }

}