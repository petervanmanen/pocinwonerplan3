package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Provincie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Provincie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProvincieRepository extends JpaRepository<Provincie, Long> {}
