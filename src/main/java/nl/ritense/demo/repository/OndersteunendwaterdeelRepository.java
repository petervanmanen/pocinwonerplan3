package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Ondersteunendwaterdeel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Ondersteunendwaterdeel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OndersteunendwaterdeelRepository extends JpaRepository<Ondersteunendwaterdeel, Long> {}
