package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Lijnengroep;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Lijnengroep entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LijnengroepRepository extends JpaRepository<Lijnengroep, Long> {}
