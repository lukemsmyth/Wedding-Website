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

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/test")
    public String test(){
        return "home/test";
    }

    public GetUserWithCustomInterfaceController() {
        super();
    }

//    @RequestMapping(value = "/test/username", method = RequestMethod.GET)
//    @ResponseBody
//    public String currentUserNameSimple() {
//        final Authentication authentication = authenticationFacade.getAuthentication();
//        return authentication.getName();
//    }

    @GetMapping("/test/username")
    public String test(Model model){
//        User user = userRepository.findByEmail(currentUserNameSimple());
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", customUserDetails.getUser());
        return "home/test-result";
    }


}