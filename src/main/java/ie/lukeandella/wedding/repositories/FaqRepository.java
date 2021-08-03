package ie.lukeandella.wedding.repositories;

import ie.lukeandella.wedding.pojos.Faq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FaqRepository extends JpaRepository<Faq, Long> {
}
