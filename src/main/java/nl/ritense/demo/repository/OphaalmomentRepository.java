package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Ophaalmoment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Ophaalmoment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OphaalmomentRepository extends JpaRepository<Ophaalmoment, Long> {}
