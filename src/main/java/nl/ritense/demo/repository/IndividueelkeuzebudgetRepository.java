package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Individueelkeuzebudget;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Individueelkeuzebudget entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IndividueelkeuzebudgetRepository extends JpaRepository<Individueelkeuzebudget, Long> {}
