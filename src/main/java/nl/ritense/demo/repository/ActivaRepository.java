package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Activa;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Activa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActivaRepository extends JpaRepository<Activa, Long> {}
