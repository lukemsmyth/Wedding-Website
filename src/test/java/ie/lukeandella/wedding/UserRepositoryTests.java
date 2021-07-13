package ie.lukeandella.wedding;

import static org.assertj.core.api.Assertions.assertThat;

import ie.lukeandella.wedding.models.User;
import ie.lukeandella.wedding.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

/*
    * This is a basic test class for testing Spring Data JPA repositories.
    * It is configured to work with the actual database
      (@AutoConfigureTestDatabase(replace = Replace.NONE)) and commit the
      changes (@Rollback(false)).
    * TestEntityManager is a wrapper of JPA’s EntityManager so we can use
      it in test class like a standard EntityManager.
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setEmail("ravikumar@gmail.com");
        user.setPassword("ravi2020");
        user.setName("Ravi");

        User savedUser = userRepository.save(user);

        User existUser = entityManager.find(User.class, savedUser.getId());

        assertThat(user.getEmail()).isEqualTo(existUser.getEmail());

    }
}
