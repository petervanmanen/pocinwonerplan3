package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonRepository
    extends JpaRepository<Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon, Long> {}
