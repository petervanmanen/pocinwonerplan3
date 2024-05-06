package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Leerling;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Leerling entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeerlingRepository extends JpaRepository<Leerling, Long> {}
