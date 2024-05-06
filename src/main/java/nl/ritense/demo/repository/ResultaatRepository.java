package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Resultaat;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Resultaat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResultaatRepository extends JpaRepository<Resultaat, Long> {}
