package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Overlijdeningeschrevenpersoon;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Overlijdeningeschrevenpersoon entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OverlijdeningeschrevenpersoonRepository extends JpaRepository<Overlijdeningeschrevenpersoon, Long> {}
