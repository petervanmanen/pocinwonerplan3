package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Naamaanschrijvingnatuurlijkpersoon;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Naamaanschrijvingnatuurlijkpersoon entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NaamaanschrijvingnatuurlijkpersoonRepository extends JpaRepository<Naamaanschrijvingnatuurlijkpersoon, Long> {}
