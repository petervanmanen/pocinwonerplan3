package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Aanduidingverblijfsrecht;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Aanduidingverblijfsrecht entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AanduidingverblijfsrechtRepository extends JpaRepository<Aanduidingverblijfsrecht, Long> {}
