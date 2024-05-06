package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Ordeningsschema;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Ordeningsschema entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrdeningsschemaRepository extends JpaRepository<Ordeningsschema, Long> {}
