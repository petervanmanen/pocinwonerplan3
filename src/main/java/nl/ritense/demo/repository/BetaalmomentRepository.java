package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Betaalmoment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Betaalmoment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BetaalmomentRepository extends JpaRepository<Betaalmoment, Long> {}
