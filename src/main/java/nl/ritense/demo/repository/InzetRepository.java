package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Inzet;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Inzet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InzetRepository extends JpaRepository<Inzet, Long> {}
