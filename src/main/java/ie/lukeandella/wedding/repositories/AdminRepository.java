package ie.lukeandella.wedding.repositories;

import ie.lukeandella.wedding.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
        }
