package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Aomaanvraagwmojeugd;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Aomaanvraagwmojeugd entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AomaanvraagwmojeugdRepository extends JpaRepository<Aomaanvraagwmojeugd, Long> {}
