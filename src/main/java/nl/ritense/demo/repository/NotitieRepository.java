package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Notitie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Notitie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NotitieRepository extends JpaRepository<Notitie, Long> {}
