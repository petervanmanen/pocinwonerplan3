package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Verplichtingwmojeugd;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Verplichtingwmojeugd entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VerplichtingwmojeugdRepository extends JpaRepository<Verplichtingwmojeugd, Long> {}
