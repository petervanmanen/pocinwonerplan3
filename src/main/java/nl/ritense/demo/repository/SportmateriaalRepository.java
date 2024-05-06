package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Sportmateriaal;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Sportmateriaal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SportmateriaalRepository extends JpaRepository<Sportmateriaal, Long> {}
