package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Reisdocumentsoort;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Reisdocumentsoort entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReisdocumentsoortRepository extends JpaRepository<Reisdocumentsoort, Long> {}
