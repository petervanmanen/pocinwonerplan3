package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Trajectactiviteitsoort;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Trajectactiviteitsoort entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrajectactiviteitsoortRepository extends JpaRepository<Trajectactiviteitsoort, Long> {}
