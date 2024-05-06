package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Redenbeeindiging;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Redenbeeindiging entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RedenbeeindigingRepository extends JpaRepository<Redenbeeindiging, Long> {}
