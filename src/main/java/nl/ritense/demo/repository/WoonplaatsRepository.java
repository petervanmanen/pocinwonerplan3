package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Woonplaats;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Woonplaats entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WoonplaatsRepository extends JpaRepository<Woonplaats, Long> {}
