package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Artikel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Artikel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArtikelRepository extends JpaRepository<Artikel, Long> {}
