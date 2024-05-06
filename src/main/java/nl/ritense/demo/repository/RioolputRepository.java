package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Rioolput;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Rioolput entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RioolputRepository extends JpaRepository<Rioolput, Long> {}
