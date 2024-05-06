package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Eobjecttypee;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Eobjecttypee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EobjecttypeeRepository extends JpaRepository<Eobjecttypee, Long> {}
