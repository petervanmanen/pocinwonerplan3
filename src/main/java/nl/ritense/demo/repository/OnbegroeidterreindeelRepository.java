package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Onbegroeidterreindeel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Onbegroeidterreindeel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OnbegroeidterreindeelRepository extends JpaRepository<Onbegroeidterreindeel, Long> {}
