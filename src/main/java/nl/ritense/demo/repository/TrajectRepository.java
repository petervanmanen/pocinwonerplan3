package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Traject;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Traject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrajectRepository extends JpaRepository<Traject, Long> {}
