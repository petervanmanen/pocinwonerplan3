package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Overigescheiding;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Overigescheiding entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OverigescheidingRepository extends JpaRepository<Overigescheiding, Long> {}
