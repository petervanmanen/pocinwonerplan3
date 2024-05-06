package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Vorderingregel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Vorderingregel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VorderingregelRepository extends JpaRepository<Vorderingregel, Long> {}
