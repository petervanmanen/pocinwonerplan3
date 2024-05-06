package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Vulling;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Vulling entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VullingRepository extends JpaRepository<Vulling, Long> {}
