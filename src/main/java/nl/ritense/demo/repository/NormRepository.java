package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Norm;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Norm entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NormRepository extends JpaRepository<Norm, Long> {}
