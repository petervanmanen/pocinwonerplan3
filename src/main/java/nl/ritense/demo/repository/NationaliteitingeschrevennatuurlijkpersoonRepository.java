package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Nationaliteitingeschrevennatuurlijkpersoon;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Nationaliteitingeschrevennatuurlijkpersoon entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NationaliteitingeschrevennatuurlijkpersoonRepository
    extends JpaRepository<Nationaliteitingeschrevennatuurlijkpersoon, Long> {}
