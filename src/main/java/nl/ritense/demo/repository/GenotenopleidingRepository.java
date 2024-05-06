package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Genotenopleiding;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Genotenopleiding entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GenotenopleidingRepository extends JpaRepository<Genotenopleiding, Long> {}
