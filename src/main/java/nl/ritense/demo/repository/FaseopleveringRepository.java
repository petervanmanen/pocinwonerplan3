package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Faseoplevering;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Faseoplevering entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FaseopleveringRepository extends JpaRepository<Faseoplevering, Long> {}
