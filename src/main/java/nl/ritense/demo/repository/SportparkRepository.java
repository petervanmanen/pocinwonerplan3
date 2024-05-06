package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Sportpark;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Sportpark entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SportparkRepository extends JpaRepository<Sportpark, Long> {}
