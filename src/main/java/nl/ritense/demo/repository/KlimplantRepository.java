package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Klimplant;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Klimplant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KlimplantRepository extends JpaRepository<Klimplant, Long> {}
