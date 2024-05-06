package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Aanvraaginkooporder;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Aanvraaginkooporder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AanvraaginkooporderRepository extends JpaRepository<Aanvraaginkooporder, Long> {}
