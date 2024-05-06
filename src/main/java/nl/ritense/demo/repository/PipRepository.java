package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Pip;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Pip entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PipRepository extends JpaRepository<Pip, Long> {}
