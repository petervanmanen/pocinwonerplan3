package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Financielesituatie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Financielesituatie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FinancielesituatieRepository extends JpaRepository<Financielesituatie, Long> {}
