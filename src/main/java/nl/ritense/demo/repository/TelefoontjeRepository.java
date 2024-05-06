package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Telefoontje;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Telefoontje entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TelefoontjeRepository extends JpaRepository<Telefoontje, Long> {}
