package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Regelvooriedereen;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Regelvooriedereen entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegelvooriedereenRepository extends JpaRepository<Regelvooriedereen, Long> {}
