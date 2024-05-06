package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Anderzaakobjectzaak;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Anderzaakobjectzaak entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnderzaakobjectzaakRepository extends JpaRepository<Anderzaakobjectzaak, Long> {}
