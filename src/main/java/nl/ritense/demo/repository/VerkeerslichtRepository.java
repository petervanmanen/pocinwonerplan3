package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Verkeerslicht;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Verkeerslicht entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VerkeerslichtRepository extends JpaRepository<Verkeerslicht, Long> {}
