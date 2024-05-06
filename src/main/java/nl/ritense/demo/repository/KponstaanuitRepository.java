package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Kponstaanuit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Kponstaanuit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KponstaanuitRepository extends JpaRepository<Kponstaanuit, Long> {}
