package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Cultuurcodebebouwd;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Cultuurcodebebouwd entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CultuurcodebebouwdRepository extends JpaRepository<Cultuurcodebebouwd, Long> {}
