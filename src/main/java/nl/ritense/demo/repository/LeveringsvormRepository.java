package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Leveringsvorm;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Leveringsvorm entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeveringsvormRepository extends JpaRepository<Leveringsvorm, Long> {}
