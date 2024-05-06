package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Bijzonderheid;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Bijzonderheid entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BijzonderheidRepository extends JpaRepository<Bijzonderheid, Long> {}
