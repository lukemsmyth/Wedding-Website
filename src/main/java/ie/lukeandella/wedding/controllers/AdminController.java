package ie.lukeandella.wedding.controllers;

import ie.lukeandella.wedding.services.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService){
        this.adminService = adminService;
    }

    @GetMapping("/admins")
    public String listAdmins(Model model){
        model.addAttribute("admins", adminService.getAdmins());
        return "admins";
    }
}
