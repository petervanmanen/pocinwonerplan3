package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Sportterrein;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Sportterrein entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SportterreinRepository extends JpaRepository<Sportterrein, Long> {}
