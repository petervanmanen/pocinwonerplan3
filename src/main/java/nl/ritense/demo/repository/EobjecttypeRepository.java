package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Eobjecttype;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Eobjecttype entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EobjecttypeRepository extends JpaRepository<Eobjecttype, String> {}
