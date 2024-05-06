package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Projectleider;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Projectleider entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectleiderRepository extends JpaRepository<Projectleider, Long> {}
