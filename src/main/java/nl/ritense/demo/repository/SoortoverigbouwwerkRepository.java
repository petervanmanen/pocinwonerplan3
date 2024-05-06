package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Soortoverigbouwwerk;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Soortoverigbouwwerk entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SoortoverigbouwwerkRepository extends JpaRepository<Soortoverigbouwwerk, Long> {}
