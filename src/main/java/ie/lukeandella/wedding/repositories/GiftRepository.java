package ie.lukeandella.wedding.repositories;

import ie.lukeandella.wedding.models.Gift;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GiftRepository extends JpaRepository<Gift, Long> {
}
