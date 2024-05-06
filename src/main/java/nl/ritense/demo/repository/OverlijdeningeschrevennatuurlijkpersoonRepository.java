package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Overlijdeningeschrevennatuurlijkpersoon;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Overlijdeningeschrevennatuurlijkpersoon entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OverlijdeningeschrevennatuurlijkpersoonRepository extends JpaRepository<Overlijdeningeschrevennatuurlijkpersoon, Long> {}
