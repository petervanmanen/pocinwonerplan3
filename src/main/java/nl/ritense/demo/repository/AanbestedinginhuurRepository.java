package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Aanbestedinginhuur;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Aanbestedinginhuur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AanbestedinginhuurRepository extends JpaRepository<Aanbestedinginhuur, Long> {}
