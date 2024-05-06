package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Klantcontact;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Klantcontact entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KlantcontactRepository extends JpaRepository<Klantcontact, Long> {}
