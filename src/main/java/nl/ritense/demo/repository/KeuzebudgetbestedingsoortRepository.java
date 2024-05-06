package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Keuzebudgetbestedingsoort;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Keuzebudgetbestedingsoort entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KeuzebudgetbestedingsoortRepository extends JpaRepository<Keuzebudgetbestedingsoort, Long> {}
