package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Kunstwerk;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Kunstwerk entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KunstwerkRepository extends JpaRepository<Kunstwerk, Long> {}
