package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Haltverwijzing;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Haltverwijzing entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HaltverwijzingRepository extends JpaRepository<Haltverwijzing, Long> {}
