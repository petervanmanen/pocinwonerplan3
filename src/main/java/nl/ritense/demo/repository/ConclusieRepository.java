package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Conclusie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Conclusie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConclusieRepository extends JpaRepository<Conclusie, Long> {}
