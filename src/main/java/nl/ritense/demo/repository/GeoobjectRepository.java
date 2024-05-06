package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Geoobject;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Geoobject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GeoobjectRepository extends JpaRepository<Geoobject, Long> {}
