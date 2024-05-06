package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Identificatiekenmerk;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Identificatiekenmerk entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IdentificatiekenmerkRepository extends JpaRepository<Identificatiekenmerk, Long> {}
