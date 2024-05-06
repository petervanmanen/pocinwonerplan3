package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Archiefcategorie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Archiefcategorie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArchiefcategorieRepository extends JpaRepository<Archiefcategorie, Long> {}
