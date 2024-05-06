package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Uren;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Uren entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UrenRepository extends JpaRepository<Uren, Long> {}
