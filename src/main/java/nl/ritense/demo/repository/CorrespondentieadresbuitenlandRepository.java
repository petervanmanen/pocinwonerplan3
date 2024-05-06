package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Correspondentieadresbuitenland;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Correspondentieadresbuitenland entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CorrespondentieadresbuitenlandRepository extends JpaRepository<Correspondentieadresbuitenland, Long> {}
