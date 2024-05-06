package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Omgevingsdocument;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Omgevingsdocument entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OmgevingsdocumentRepository extends JpaRepository<Omgevingsdocument, Long> {}
