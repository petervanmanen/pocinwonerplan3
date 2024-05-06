package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Vegetatieobject;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Vegetatieobject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VegetatieobjectRepository extends JpaRepository<Vegetatieobject, Long> {}
