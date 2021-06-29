package ie.lukeandella.wedding.repositories;

import ie.lukeandella.wedding.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
