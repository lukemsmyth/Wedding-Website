package ie.lukeandella.wedding.configuration;

import ie.lukeandella.wedding.models.Gift;
import ie.lukeandella.wedding.repositories.GiftRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    CommandLineRunner commandLineRunner(GiftRepository repository){
        return args -> {

            Gift shoes = new Gift();
            shoes.setName("Shoes");
            shoes.setLink("schuh.com");
            shoes.setDescription("A nice pair of shoes");
            shoes.setPrice(89.99);
            repository.save(shoes);

            Gift jumper = new Gift();
            jumper.setName("Jumper");
            jumper.setLink("jumpers.com");
            jumper.setDescription("A nice jumper");
            jumper.setPrice(67.99);
            repository.save(jumper);

        };
    }
}
