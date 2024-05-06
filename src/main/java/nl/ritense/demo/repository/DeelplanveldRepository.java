package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Deelplanveld;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Deelplanveld entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeelplanveldRepository extends JpaRepository<Deelplanveld, Long> {}
