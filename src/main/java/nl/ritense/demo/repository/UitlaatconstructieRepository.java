package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Uitlaatconstructie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Uitlaatconstructie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UitlaatconstructieRepository extends JpaRepository<Uitlaatconstructie, Long> {}
