package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Locatieaanduidingadreswozobject;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Locatieaanduidingadreswozobject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocatieaanduidingadreswozobjectRepository extends JpaRepository<Locatieaanduidingadreswozobject, Long> {}
