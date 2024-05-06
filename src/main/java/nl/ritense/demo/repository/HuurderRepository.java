package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Huurder;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Huurder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HuurderRepository extends JpaRepository<Huurder, Long> {}
