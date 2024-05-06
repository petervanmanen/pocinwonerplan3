package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Ondersteunendwegdeel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Ondersteunendwegdeel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OndersteunendwegdeelRepository extends JpaRepository<Ondersteunendwegdeel, Long> {}
