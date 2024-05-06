package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Classa;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Classa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClassaRepository extends JpaRepository<Classa, Long> {}
