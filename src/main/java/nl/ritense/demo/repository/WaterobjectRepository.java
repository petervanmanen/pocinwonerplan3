package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Waterobject;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Waterobject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WaterobjectRepository extends JpaRepository<Waterobject, Long> {}
