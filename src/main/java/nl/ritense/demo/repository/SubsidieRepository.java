package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Subsidie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Subsidie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubsidieRepository extends JpaRepository<Subsidie, Long> {}
