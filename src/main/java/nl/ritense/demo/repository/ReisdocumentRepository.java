package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Reisdocument;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Reisdocument entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReisdocumentRepository extends JpaRepository<Reisdocument, Long> {}
