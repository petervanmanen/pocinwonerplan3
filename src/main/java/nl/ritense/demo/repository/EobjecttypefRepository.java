package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Eobjecttypef;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Eobjecttypef entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EobjecttypefRepository extends JpaRepository<Eobjecttypef, Long> {}
