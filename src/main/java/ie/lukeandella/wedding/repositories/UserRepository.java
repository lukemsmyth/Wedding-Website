package ie.lukeandella.wedding.repositories;

import ie.lukeandella.wedding.pojos.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.name = ?1")
    User findByName(String username);

    @Query("SELECT u FROM User u WHERE u.verificationCode = ?1")
    User findByVerificationCode(String code);

    @Query("SELECT u FROM User u WHERE u.name = ?1")
    User findByEmail(String email);

    @Query
    List<User> findAllByIdIsNot(Long currentUserId);

    @Query
    boolean existsByEmailEquals(String email);
}
