package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Speelterrein;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Speelterrein entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpeelterreinRepository extends JpaRepository<Speelterrein, Long> {}
