package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Erfgoedobject;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Erfgoedobject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ErfgoedobjectRepository extends JpaRepository<Erfgoedobject, Long> {}
