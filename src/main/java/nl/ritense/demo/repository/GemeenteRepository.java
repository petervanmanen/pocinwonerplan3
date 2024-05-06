package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Gemeente;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Gemeente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GemeenteRepository extends JpaRepository<Gemeente, Long> {}
