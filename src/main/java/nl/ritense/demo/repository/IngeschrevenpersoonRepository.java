package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Ingeschrevenpersoon;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Ingeschrevenpersoon entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IngeschrevenpersoonRepository extends JpaRepository<Ingeschrevenpersoon, Long> {}
