package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Eobjecttypea;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Eobjecttypea entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EobjecttypeaRepository extends JpaRepository<Eobjecttypea, Long> {}
