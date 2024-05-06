package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Taak;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Taak entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaakRepository extends JpaRepository<Taak, Long> {}
