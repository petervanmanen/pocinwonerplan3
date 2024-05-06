package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Meldingeigenbijdrage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Meldingeigenbijdrage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MeldingeigenbijdrageRepository extends JpaRepository<Meldingeigenbijdrage, Long> {}
