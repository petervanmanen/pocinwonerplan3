package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Partij;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Partij entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartijRepository extends JpaRepository<Partij, Long> {}
