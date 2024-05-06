package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Verhuurbaareenheid;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Verhuurbaareenheid entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VerhuurbaareenheidRepository extends JpaRepository<Verhuurbaareenheid, Long> {}
