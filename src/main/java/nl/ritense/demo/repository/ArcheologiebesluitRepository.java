package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Archeologiebesluit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Archeologiebesluit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArcheologiebesluitRepository extends JpaRepository<Archeologiebesluit, Long> {}
