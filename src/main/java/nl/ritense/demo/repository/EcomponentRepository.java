package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Ecomponent;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Ecomponent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EcomponentRepository extends JpaRepository<Ecomponent, Long> {}
