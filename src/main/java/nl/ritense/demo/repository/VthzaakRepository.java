package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Vthzaak;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Vthzaak entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VthzaakRepository extends JpaRepository<Vthzaak, Long> {}
