package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Budgetuitputting;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Budgetuitputting entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BudgetuitputtingRepository extends JpaRepository<Budgetuitputting, Long> {}
