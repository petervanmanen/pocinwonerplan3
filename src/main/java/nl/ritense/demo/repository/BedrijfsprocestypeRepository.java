package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Bedrijfsprocestype;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Bedrijfsprocestype entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BedrijfsprocestypeRepository extends JpaRepository<Bedrijfsprocestype, Long> {}
