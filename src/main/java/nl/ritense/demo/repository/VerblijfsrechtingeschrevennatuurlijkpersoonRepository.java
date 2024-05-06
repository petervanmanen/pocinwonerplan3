package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Verblijfsrechtingeschrevennatuurlijkpersoon;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Verblijfsrechtingeschrevennatuurlijkpersoon entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VerblijfsrechtingeschrevennatuurlijkpersoonRepository
    extends JpaRepository<Verblijfsrechtingeschrevennatuurlijkpersoon, Long> {}
