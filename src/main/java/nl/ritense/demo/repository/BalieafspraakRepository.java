package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Balieafspraak;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Balieafspraak entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BalieafspraakRepository extends JpaRepository<Balieafspraak, Long> {}
