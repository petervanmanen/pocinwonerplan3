package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Prijsregel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Prijsregel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrijsregelRepository extends JpaRepository<Prijsregel, Long> {}
