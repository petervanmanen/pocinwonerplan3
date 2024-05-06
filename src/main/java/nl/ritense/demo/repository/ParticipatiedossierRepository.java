package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Participatiedossier;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Participatiedossier entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParticipatiedossierRepository extends JpaRepository<Participatiedossier, Long> {}
