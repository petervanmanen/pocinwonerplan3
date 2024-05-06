package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Cultuuronbebouwd;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Cultuuronbebouwd entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CultuuronbebouwdRepository extends JpaRepository<Cultuuronbebouwd, Long> {}
