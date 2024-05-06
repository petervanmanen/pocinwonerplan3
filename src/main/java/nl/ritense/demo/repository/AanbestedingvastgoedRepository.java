package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Aanbestedingvastgoed;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Aanbestedingvastgoed entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AanbestedingvastgoedRepository extends JpaRepository<Aanbestedingvastgoed, Long> {}
