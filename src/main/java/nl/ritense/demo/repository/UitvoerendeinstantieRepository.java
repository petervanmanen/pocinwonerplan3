package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Uitvoerendeinstantie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Uitvoerendeinstantie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UitvoerendeinstantieRepository extends JpaRepository<Uitvoerendeinstantie, Long> {}
