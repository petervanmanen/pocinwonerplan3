package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Overigbouwwerk;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Overigbouwwerk entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OverigbouwwerkRepository extends JpaRepository<Overigbouwwerk, Long> {}
