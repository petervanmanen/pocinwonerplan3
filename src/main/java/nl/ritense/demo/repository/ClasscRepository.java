package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Classc;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Classc entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClasscRepository extends JpaRepository<Classc, Long> {}
