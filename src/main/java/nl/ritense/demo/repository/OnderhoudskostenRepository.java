package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Onderhoudskosten;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Onderhoudskosten entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OnderhoudskostenRepository extends JpaRepository<Onderhoudskosten, Long> {}
