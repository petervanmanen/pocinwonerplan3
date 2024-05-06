package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Gunning;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Gunning entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GunningRepository extends JpaRepository<Gunning, Long> {}
