package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Telefoonstatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Telefoonstatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TelefoonstatusRepository extends JpaRepository<Telefoonstatus, Long> {}
