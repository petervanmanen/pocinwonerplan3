package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Vervoerder;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Vervoerder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VervoerderRepository extends JpaRepository<Vervoerder, Long> {}
