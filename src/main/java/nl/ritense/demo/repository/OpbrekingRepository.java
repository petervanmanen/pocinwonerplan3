package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Opbreking;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Opbreking entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OpbrekingRepository extends JpaRepository<Opbreking, Long> {}
