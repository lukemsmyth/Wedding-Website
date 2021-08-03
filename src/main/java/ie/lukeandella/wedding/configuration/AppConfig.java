package ie.lukeandella.wedding.configuration;

import ie.lukeandella.wedding.pojos.Faq;
import ie.lukeandella.wedding.pojos.Gift;
import ie.lukeandella.wedding.pojos.Itinerary;
import ie.lukeandella.wedding.pojos.Role;
import ie.lukeandella.wedding.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

@Configuration
public class AppConfig {


    @Bean
    CommandLineRunner commandLineRunner(GiftRepository giftRepository,
                                        UserRepository userRepository,
                                        RoleRepository roleRepository,
                                        FaqRepository faqRepository,
                                        ItineraryRepository itineraryRepository)
    {
        return args -> {
            String smokedSalmon = "Smoked salmon is a preparation of salmon, typically a fillet that has been cured and hot or cold smoked.Due to its moderately high price, smoked salmon is considered a delicacy.";
            Itinerary it1 = new Itinerary(LocalDateTime.of(2020, 6, 15, 12, 30),"Sandycove", "Swim at the Forty Foot", "Going for a dip at the Forty Foot", LocalDateTime.now());
            itineraryRepository.save(it1);
            Itinerary it2 = new Itinerary(LocalDateTime.of(2020, 6, 16, 13, 30),"Clontarf", "Walk in the park", "Going for a walk in St. Anne's Park", LocalDateTime.now());
            itineraryRepository.save(it2);
            Itinerary it3 = new Itinerary(LocalDateTime.of(2020, 6, 17, 18, 30),"Dublin", "Night on the beer", "Going for a night on the beer", LocalDateTime.now());
            itineraryRepository.save(it3);
        };
    }




//
//            Role admin = new Role("ADMIN");
//            roleRepository.save(admin);
//            Role user = new Role("USER");
//            roleRepository.save(user);
//
//            Date now = new Date();
//            Faq faq1 = new Faq(
//                    "Are children invited?",
//                    new Timestamp(now.getTime())
//            );
//            faqRepository.save(faq1);
//            Faq faq2 = new Faq(
//                    "Are dogs invited?",
//                      smokedSalmon,
//                    new Timestamp(now.getTime())
//            );
//            faqRepository.save(faq2);
//            Faq faq3 = new Faq(
//                    "Are grannies invited?",
//    smokedSalmon,
//                    new Timestamp(now.getTime())
//            );
//            faqRepository.save(faq3);
//            Faq faq4 = new Faq(
//                    "Are grandas invited?",
// smokedSalmon
//                    new Timestamp(now.getTime())
//            );
//            faqRepository.save(faq4);
//            Faq faq5 = new Faq(
//                    "Are cats invited?",
//smokedSalmon,
//                    new Timestamp(now.getTime())
//            );
//            faqRepository.save(faq4);
//
//            Gift laptop = new Gift("Laptop", "A Lenovo laptop.", 1000.00, "null", "laptopsdirect.ie", true, 0, true);
//            giftRepository.save(laptop);
//
//            Gift shoes = new Gift();
//            shoes.setName("Shoes (splitable)");
//            shoes.setLink("schuh.com");
//            shoes.setDescription("A nice pair of shoes");
//            shoes.setPrice(89.99);
//            shoes.setSplitable(true);
//            shoes.setPercentageReserved(0);
//            giftRepository.save(shoes);
//
//            Gift jumper = new Gift();
//            jumper.setName("Jumpe (not splitable)");
//            jumper.setLink("jumpers.com");
//            jumper.setDescription("A nice jumper");
//            jumper.setPrice(67.99);
//            jumper.setSplitable(false);
//            jumper.setPercentageReserved(0);
//            giftRepository.save(jumper);
//
//            Gift oranges = new Gift("Oranges", "A nice bag of oranges.", 100.00, "null", "orange.com", true, 0, true);
//            giftRepository.save(oranges);
//


}
