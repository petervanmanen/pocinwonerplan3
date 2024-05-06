package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Fractie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Fractie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FractieRepository extends JpaRepository<Fractie, Long> {}
