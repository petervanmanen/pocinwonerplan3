package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Kaart;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Kaart entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KaartRepository extends JpaRepository<Kaart, Long> {}
