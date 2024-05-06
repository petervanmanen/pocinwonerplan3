package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Gebouw;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Gebouw entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GebouwRepository extends JpaRepository<Gebouw, Long> {}
