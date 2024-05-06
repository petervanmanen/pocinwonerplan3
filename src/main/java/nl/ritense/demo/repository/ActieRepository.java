package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Actie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Actie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActieRepository extends JpaRepository<Actie, Long> {}
