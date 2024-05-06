package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Eobjectrelatie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Eobjectrelatie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EobjectrelatieRepository extends JpaRepository<Eobjectrelatie, Long> {}
