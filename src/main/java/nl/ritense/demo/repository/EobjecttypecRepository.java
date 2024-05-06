package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Eobjecttypec;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Eobjecttypec entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EobjecttypecRepository extends JpaRepository<Eobjecttypec, Long> {}
