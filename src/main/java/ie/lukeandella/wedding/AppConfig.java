package ie.lukeandella.wedding;

import ie.lukeandella.wedding.models.Gift;
import ie.lukeandella.wedding.models.User;
import ie.lukeandella.wedding.repositories.AdminRepository;
import ie.lukeandella.wedding.repositories.GiftRepository;
import ie.lukeandella.wedding.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    CommandLineRunner commandLineRunner(
            GiftRepository giftRepository,
            UserRepository userRepository,
            AdminRepository adminRepository)
    {
        return args ->
        {
            Gift golfClubs = new Gift();
            golfClubs.setName("Golf Clubs");
            Gift recordPlayer = new Gift();
            recordPlayer.setName("Record PLayer");
            User dave = new User();
            dave.setUsername("Dave");
            User john = new User();
            john.setUsername("John");
            john.addGift(golfClubs);
            golfClubs.setPercentageReserved(50);
            dave.addGift(golfClubs);
            golfClubs.setPercentageReserved(50);
            dave.addGift(recordPlayer);
            recordPlayer.setPercentageReserved(100);
        };
    }




}
