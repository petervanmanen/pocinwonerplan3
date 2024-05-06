package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Locatieaanduidingwozobject;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Locatieaanduidingwozobject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocatieaanduidingwozobjectRepository extends JpaRepository<Locatieaanduidingwozobject, Long> {}
