package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Cultuurcodeonbebouwd;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Cultuurcodeonbebouwd entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CultuurcodeonbebouwdRepository extends JpaRepository<Cultuurcodeonbebouwd, Long> {}
