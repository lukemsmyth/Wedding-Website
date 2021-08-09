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

            /*
             * Initialise the application with 3 users.
             */

            //Make a password
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String password = encoder.encode("0000");
            //Make admin
            Role adminRole = new Role("ADMIN");
            User adminUser = new User("admin@x.com", password, true);
            adminUser.addRole(adminRole);
            userRepository.save(adminUser);
            roleRepository.save(adminRole);
            //Make member
            Role memberRole = new Role("MEMBER");
            User memberUser = new User("member@x.com", password, true);
            memberUser.addRole(memberRole);
            userRepository.save(memberUser);
            roleRepository.save(memberRole);
            //Make visitor
            Role visitorRole = new Role("VISITOR");
            User visitorUser = new User("visitor@x.com", password, true);
            visitorUser.addRole(visitorRole);
            userRepository.save(visitorUser);
            roleRepository.save(visitorRole);

            /*
             * Initialise the application with some throwaway data.
             */
            Itinerary it1 = new Itinerary(LocalDateTime.of(2020, 6, 15, 12, 30),"Sandycove", "Swim at the Forty Foot", "Going for a dip at the Forty Foot.", LocalDateTime.now());
            itineraryRepository.save(it1);
            Itinerary it2 = new Itinerary(LocalDateTime.of(2020, 6, 16, 13, 30),"Clontarf", "Walk in the park", "Going for a walk in St. Anne's Park.", LocalDateTime.now());
            itineraryRepository.save(it2);
            Itinerary it3 = new Itinerary(LocalDateTime.of(2020, 6, 17, 18, 30),"Dublin", "Night on the beer", "Going for a night on the beer.", LocalDateTime.now());
            itineraryRepository.save(it3);
            Itinerary it4 = new Itinerary(LocalDateTime.of(2020, 6, 18, 18, 30),"Smock Alley Theatre", "The Big Day", "Wedding ceremony.", LocalDateTime.now());
            itineraryRepository.save(it4);

            String smokedSalmon = "Smoked salmon is a preparation of salmon, typically a fillet that has been cured and hot or cold smoked. Due to its moderately high price, smoked salmon is considered a delicacy.";
            Faq faq1 = new Faq("Are children invited?", smokedSalmon, LocalDateTime.now());
            faqRepository.save(faq1);
            Faq faq2 = new Faq("Are dogs invited?", smokedSalmon, LocalDateTime.now());
            faqRepository.save(faq2);
            Faq faq3 = new Faq("Are cats invited?", smokedSalmon, LocalDateTime.now());
            faqRepository.save(faq3);
            Faq faq4 = new Faq("Are goats invited?", smokedSalmon, LocalDateTime.now());
            faqRepository.save(faq4);
            Faq faq5 = new Faq("Am I invited?", smokedSalmon, LocalDateTime.now());
            faqRepository.save(faq5);

            Gift laptop = new Gift("Laptop", "A Lenovo laptop.", 1000.00, "null", "laptopsdirect.ie", false, 0, true);
            giftRepository.save(laptop);
            Gift shoes = new Gift("Shoes", "A nice pair of shoes", 99.99, "N/A", "schuh.com", false, 0, true);
            giftRepository.save(shoes);
            Gift oranges = new Gift("Oranges", "A nice bag of oranges.", 100.00, "null", "orange.com", true, 0, true);
            giftRepository.save(oranges);
            Gift apples = new Gift("Apples", "A nice bag of apples.", 100.00, "null", "apple.com", false, 0, true);
            giftRepository.save(apples);
        };
    }

}
