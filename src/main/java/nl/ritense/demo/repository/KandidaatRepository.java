package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Kandidaat;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Kandidaat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KandidaatRepository extends JpaRepository<Kandidaat, Long> {}
