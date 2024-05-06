package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Vastgoedcontractregel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Vastgoedcontractregel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VastgoedcontractregelRepository extends JpaRepository<Vastgoedcontractregel, Long> {}
