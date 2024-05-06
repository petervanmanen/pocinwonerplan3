package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Precario;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Precario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrecarioRepository extends JpaRepository<Precario, Long> {}
