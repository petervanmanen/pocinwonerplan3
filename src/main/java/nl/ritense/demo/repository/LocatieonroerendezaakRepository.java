package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Locatieonroerendezaak;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Locatieonroerendezaak entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocatieonroerendezaakRepository extends JpaRepository<Locatieonroerendezaak, Long> {}
