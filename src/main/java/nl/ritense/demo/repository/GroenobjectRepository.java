package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Groenobject;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Groenobject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GroenobjectRepository extends JpaRepository<Groenobject, Long> {}
