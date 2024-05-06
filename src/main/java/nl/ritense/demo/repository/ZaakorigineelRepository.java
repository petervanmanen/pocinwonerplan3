package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Zaakorigineel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Zaakorigineel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ZaakorigineelRepository extends JpaRepository<Zaakorigineel, Long> {}
