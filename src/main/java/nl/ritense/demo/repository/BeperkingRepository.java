package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Beperking;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Beperking entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BeperkingRepository extends JpaRepository<Beperking, Long> {}
