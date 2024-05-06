package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Inkomensvoorzieningsoort;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Inkomensvoorzieningsoort entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InkomensvoorzieningsoortRepository extends JpaRepository<Inkomensvoorzieningsoort, Long> {}
