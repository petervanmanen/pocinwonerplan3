package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Brug;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Brug entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BrugRepository extends JpaRepository<Brug, Long> {}
