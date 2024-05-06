package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Vestigingvanzaakbehandelendeorganisatie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Vestigingvanzaakbehandelendeorganisatie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VestigingvanzaakbehandelendeorganisatieRepository extends JpaRepository<Vestigingvanzaakbehandelendeorganisatie, Long> {}
