package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Beslissing;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Beslissing entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BeslissingRepository extends JpaRepository<Beslissing, Long> {}
