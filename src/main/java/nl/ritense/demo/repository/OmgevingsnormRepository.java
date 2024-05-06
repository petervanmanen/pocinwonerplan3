package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Omgevingsnorm;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Omgevingsnorm entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OmgevingsnormRepository extends JpaRepository<Omgevingsnorm, Long> {}
