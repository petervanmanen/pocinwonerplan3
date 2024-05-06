package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Onderwerp;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Onderwerp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OnderwerpRepository extends JpaRepository<Onderwerp, Long> {}
