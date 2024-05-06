package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Strooidag;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Strooidag entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StrooidagRepository extends JpaRepository<Strooidag, Long> {}
