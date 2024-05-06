package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Overbruggingsdeel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Overbruggingsdeel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OverbruggingsdeelRepository extends JpaRepository<Overbruggingsdeel, Long> {}
