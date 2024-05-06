package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Trajectactiviteit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Trajectactiviteit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrajectactiviteitRepository extends JpaRepository<Trajectactiviteit, Long> {}
