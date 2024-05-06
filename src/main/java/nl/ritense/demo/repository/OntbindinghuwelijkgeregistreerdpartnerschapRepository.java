package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Ontbindinghuwelijkgeregistreerdpartnerschap;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Ontbindinghuwelijkgeregistreerdpartnerschap entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OntbindinghuwelijkgeregistreerdpartnerschapRepository
    extends JpaRepository<Ontbindinghuwelijkgeregistreerdpartnerschap, Long> {}
