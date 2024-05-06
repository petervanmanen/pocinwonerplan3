package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Ecomponentsoort;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Ecomponentsoort entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EcomponentsoortRepository extends JpaRepository<Ecomponentsoort, Long> {}
