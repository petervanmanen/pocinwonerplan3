package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Formuliersoortveld;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Formuliersoortveld entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FormuliersoortveldRepository extends JpaRepository<Formuliersoortveld, Long> {}
