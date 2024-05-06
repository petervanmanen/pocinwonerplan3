package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Vastgoedobject;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Vastgoedobject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VastgoedobjectRepository extends JpaRepository<Vastgoedobject, Long> {}
