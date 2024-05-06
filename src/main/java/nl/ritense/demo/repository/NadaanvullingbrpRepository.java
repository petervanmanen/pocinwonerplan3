package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Nadaanvullingbrp;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Nadaanvullingbrp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NadaanvullingbrpRepository extends JpaRepository<Nadaanvullingbrp, Long> {}
