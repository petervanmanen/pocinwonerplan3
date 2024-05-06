package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Vervoersmiddel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Vervoersmiddel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VervoersmiddelRepository extends JpaRepository<Vervoersmiddel, Long> {}
