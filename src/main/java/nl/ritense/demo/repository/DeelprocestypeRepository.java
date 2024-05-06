package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Deelprocestype;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Deelprocestype entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeelprocestypeRepository extends JpaRepository<Deelprocestype, Long> {}
