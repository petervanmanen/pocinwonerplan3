package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Wozobject;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Wozobject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WozobjectRepository extends JpaRepository<Wozobject, Long> {}
