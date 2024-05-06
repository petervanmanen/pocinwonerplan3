package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Subsidiebeschikking;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Subsidiebeschikking entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubsidiebeschikkingRepository extends JpaRepository<Subsidiebeschikking, Long> {}
