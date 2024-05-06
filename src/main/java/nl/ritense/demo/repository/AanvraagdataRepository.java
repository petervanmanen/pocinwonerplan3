package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Aanvraagdata;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Aanvraagdata entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AanvraagdataRepository extends JpaRepository<Aanvraagdata, Long> {}
