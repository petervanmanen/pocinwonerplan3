package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Factuurregel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Factuurregel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FactuurregelRepository extends JpaRepository<Factuurregel, Long> {}
