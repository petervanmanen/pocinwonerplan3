package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Relatiesoort;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Relatiesoort entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RelatiesoortRepository extends JpaRepository<Relatiesoort, Long> {}
