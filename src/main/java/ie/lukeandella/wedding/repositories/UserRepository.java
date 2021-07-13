package ie.lukeandella.wedding.repositories;

import ie.lukeandella.wedding.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.name = ?1")
    public User findByName(String username);

    @Query("SELECT u FROM User u WHERE u.verificationCode = ?1")
    public User findByVerificationCode(String code);

    @Query("SELECT u FROM User u WHERE u.name = ?1")
    User findByEmail(String email);
}
