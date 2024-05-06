package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Procesverbaalonderwijs;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Procesverbaalonderwijs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcesverbaalonderwijsRepository extends JpaRepository<Procesverbaalonderwijs, Long> {}
