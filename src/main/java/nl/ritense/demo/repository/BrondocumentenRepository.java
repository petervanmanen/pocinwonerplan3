package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Brondocumenten;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Brondocumenten entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BrondocumentenRepository extends JpaRepository<Brondocumenten, Long> {}
