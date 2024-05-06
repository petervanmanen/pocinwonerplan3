package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Formulierverlenginginhuur;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Formulierverlenginginhuur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FormulierverlenginginhuurRepository extends JpaRepository<Formulierverlenginginhuur, Long> {}
