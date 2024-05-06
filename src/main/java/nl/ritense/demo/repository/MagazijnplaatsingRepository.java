package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Magazijnplaatsing;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Magazijnplaatsing entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MagazijnplaatsingRepository extends JpaRepository<Magazijnplaatsing, Long> {}
