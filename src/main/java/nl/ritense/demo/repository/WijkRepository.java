package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Wijk;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Wijk entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WijkRepository extends JpaRepository<Wijk, Long> {}
