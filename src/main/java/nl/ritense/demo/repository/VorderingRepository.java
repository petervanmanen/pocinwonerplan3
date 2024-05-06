package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Vordering;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Vordering entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VorderingRepository extends JpaRepository<Vordering, Long> {}
