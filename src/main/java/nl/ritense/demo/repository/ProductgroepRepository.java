package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Productgroep;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Productgroep entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductgroepRepository extends JpaRepository<Productgroep, Long> {}
