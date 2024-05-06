package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Verkeersbesluit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Verkeersbesluit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VerkeersbesluitRepository extends JpaRepository<Verkeersbesluit, Long> {}
