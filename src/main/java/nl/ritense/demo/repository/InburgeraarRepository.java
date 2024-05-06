package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Inburgeraar;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Inburgeraar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InburgeraarRepository extends JpaRepository<Inburgeraar, Long> {}
