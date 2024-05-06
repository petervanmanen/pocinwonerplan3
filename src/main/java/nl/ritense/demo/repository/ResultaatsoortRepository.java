package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Resultaatsoort;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Resultaatsoort entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResultaatsoortRepository extends JpaRepository<Resultaatsoort, Long> {}
