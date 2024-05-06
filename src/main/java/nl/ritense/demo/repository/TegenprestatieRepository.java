package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Tegenprestatie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Tegenprestatie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TegenprestatieRepository extends JpaRepository<Tegenprestatie, Long> {}
