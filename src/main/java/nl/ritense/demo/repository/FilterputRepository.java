package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Filterput;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Filterput entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FilterputRepository extends JpaRepository<Filterput, Long> {}
