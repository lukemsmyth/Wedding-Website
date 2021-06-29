package ie.lukeandella.wedding.services;

import ie.lukeandella.wedding.models.Admin;
import ie.lukeandella.wedding.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository){
        this.adminRepository = adminRepository;
    }

    public List<Admin> getAdmins(){
        return adminRepository.findAll();
    }

    public void addAdmin(Admin admin){
        adminRepository.save(admin);
    }

    public void deleteAdmin(Long adminId){
        if(!adminRepository.existsById(adminId)){
            throw new IllegalStateException("Admin with id " + adminId + " does not exist.");
        }
        adminRepository.deleteById(adminId);
    }

    @Transactional
    public void updateAuthorName(Long adminId, String username, char[] password){
        Admin author = adminRepository.findById(adminId)
                .orElseThrow(() -> new IllegalStateException(
                        "Admin with id " + adminId + " does not exist."
                ));
        //Just some very simple logic to start
        //Only update the name if a non-empty String is passed
        if(!username.isEmpty()){
            author.setUsername(username);
        }
        if(password.length == 0){
            author.setPassword(password);
        }
    }


}
