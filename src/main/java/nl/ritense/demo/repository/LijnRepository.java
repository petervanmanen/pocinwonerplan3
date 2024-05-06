package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Lijn;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Lijn entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LijnRepository extends JpaRepository<Lijn, Long> {}
