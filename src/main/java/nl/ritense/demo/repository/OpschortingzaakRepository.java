package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Opschortingzaak;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Opschortingzaak entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OpschortingzaakRepository extends JpaRepository<Opschortingzaak, Long> {}
