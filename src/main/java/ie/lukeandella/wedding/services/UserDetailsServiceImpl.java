package ie.lukeandella.wedding.services;

import ie.lukeandella.wedding.pojos.CustomUserDetails;
import ie.lukeandella.wedding.pojos.User;
import ie.lukeandella.wedding.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
        return new CustomUserDetails(user);
    }

}
