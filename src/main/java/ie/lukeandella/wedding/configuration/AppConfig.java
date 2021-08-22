package ie.lukeandella.wedding.configuration;

import ie.lukeandella.wedding.pojos.*;
import ie.lukeandella.wedding.repositories.*;
import ie.lukeandella.wedding.services.RoleService;
import ie.lukeandella.wedding.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.spring5.ISpringTemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolution;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@Configuration
public class AppConfig {

    @Bean
    CommandLineRunner commandLineRunner(GiftRepository giftRepository,
                                        UserRepository userRepository,
                                        RoleRepository roleRepository,
                                        FaqRepository faqRepository,
                                        ItineraryRepository itineraryRepository,
                                        UserService userService,
                                        RoleService roleService)
    {
        return args -> {

            userRepository.deleteById(80L);
            userRepository.deleteById(82L);
            userRepository.deleteById(84L);

        };
    }

}
