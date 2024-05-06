package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Tarief;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Tarief entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TariefRepository extends JpaRepository<Tarief, Long> {}
