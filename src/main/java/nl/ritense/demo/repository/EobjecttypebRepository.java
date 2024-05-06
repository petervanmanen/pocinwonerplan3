package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Eobjecttypeb;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Eobjecttypeb entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EobjecttypebRepository extends JpaRepository<Eobjecttypeb, Long> {}
