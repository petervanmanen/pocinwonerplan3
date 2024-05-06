package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Soortwozobject;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Soortwozobject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SoortwozobjectRepository extends JpaRepository<Soortwozobject, Long> {}
