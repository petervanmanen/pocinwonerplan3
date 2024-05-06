package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Datatype;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Datatype entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DatatypeRepository extends JpaRepository<Datatype, String> {}
