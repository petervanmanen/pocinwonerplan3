package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Participatiecomponent;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Participatiecomponent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParticipatiecomponentRepository extends JpaRepository<Participatiecomponent, Long> {}
