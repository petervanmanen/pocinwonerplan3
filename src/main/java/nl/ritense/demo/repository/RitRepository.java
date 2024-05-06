package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Rit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Rit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RitRepository extends JpaRepository<Rit, Long> {}
