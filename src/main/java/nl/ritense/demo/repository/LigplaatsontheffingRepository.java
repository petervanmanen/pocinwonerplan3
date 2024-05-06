package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Ligplaatsontheffing;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Ligplaatsontheffing entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LigplaatsontheffingRepository extends JpaRepository<Ligplaatsontheffing, Long> {}
