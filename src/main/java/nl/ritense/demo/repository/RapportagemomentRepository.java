package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Rapportagemoment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Rapportagemoment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RapportagemomentRepository extends JpaRepository<Rapportagemoment, Long> {}
