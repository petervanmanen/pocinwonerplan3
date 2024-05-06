package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Landofgebied;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Landofgebied entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LandofgebiedRepository extends JpaRepository<Landofgebied, Long> {}
