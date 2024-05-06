package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Bergingsbassin;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Bergingsbassin entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BergingsbassinRepository extends JpaRepository<Bergingsbassin, Long> {}
