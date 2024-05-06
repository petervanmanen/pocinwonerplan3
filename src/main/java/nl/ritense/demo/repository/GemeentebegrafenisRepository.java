package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Gemeentebegrafenis;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Gemeentebegrafenis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GemeentebegrafenisRepository extends JpaRepository<Gemeentebegrafenis, Long> {}
