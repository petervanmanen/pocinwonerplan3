package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Eobjecttyped;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Eobjecttyped entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EobjecttypedRepository extends JpaRepository<Eobjecttyped, Long> {}
