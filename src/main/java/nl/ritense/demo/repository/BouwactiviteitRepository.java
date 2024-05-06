package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Bouwactiviteit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Bouwactiviteit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BouwactiviteitRepository extends JpaRepository<Bouwactiviteit, Long> {}
