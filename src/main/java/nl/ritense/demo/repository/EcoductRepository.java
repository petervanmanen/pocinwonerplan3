package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Ecoduct;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Ecoduct entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EcoductRepository extends JpaRepository<Ecoduct, Long> {}
