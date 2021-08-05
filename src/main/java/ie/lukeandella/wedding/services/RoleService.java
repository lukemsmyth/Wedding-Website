package ie.lukeandella.wedding.services;

import ie.lukeandella.wedding.exceptions.RoleNotExistsException;
import ie.lukeandella.wedding.pojos.Role;
import ie.lukeandella.wedding.repositories.ItineraryRepository;
import ie.lukeandella.wedding.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class RoleService {

    @Autowired
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    public Role getRoleByName(String name) throws RoleNotExistsException {
        List<Role> roles = roleRepository.findAll();
        for(Role role : roles){
            if(role.getName().equals(name)){
                return role;
            }
        }
        throw new RoleNotExistsException("No role exists with the name " + name + ".");
    }

}
