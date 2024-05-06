package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Rol;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Rol entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {}
