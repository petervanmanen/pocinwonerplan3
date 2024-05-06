package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Beschikking;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Beschikking entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BeschikkingRepository extends JpaRepository<Beschikking, Long> {}
