package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Bezetting;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Bezetting entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BezettingRepository extends JpaRepository<Bezetting, Long> {}
