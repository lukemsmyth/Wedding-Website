package ie.lukeandella.wedding.controllers;

import ie.lukeandella.wedding.configuration.IAuthenticationFacade;
import ie.lukeandella.wedding.pojos.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GetUserWithCustomInterfaceController {

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    public GetUserWithCustomInterfaceController() {
        super();
    }

}