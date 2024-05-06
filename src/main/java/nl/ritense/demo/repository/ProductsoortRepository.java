package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Productsoort;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Productsoort entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductsoortRepository extends JpaRepository<Productsoort, Long> {}
