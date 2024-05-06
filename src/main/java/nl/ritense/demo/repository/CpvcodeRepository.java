package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Cpvcode;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Cpvcode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CpvcodeRepository extends JpaRepository<Cpvcode, Long> {}
