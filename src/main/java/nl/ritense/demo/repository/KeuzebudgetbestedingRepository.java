package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Keuzebudgetbesteding;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Keuzebudgetbesteding entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KeuzebudgetbestedingRepository extends JpaRepository<Keuzebudgetbesteding, Long> {}
