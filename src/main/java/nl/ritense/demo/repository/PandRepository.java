package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Pand;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Pand entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PandRepository extends JpaRepository<Pand, Long> {}
