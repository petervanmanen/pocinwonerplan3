package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Prijs;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Prijs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrijsRepository extends JpaRepository<Prijs, Long> {}
