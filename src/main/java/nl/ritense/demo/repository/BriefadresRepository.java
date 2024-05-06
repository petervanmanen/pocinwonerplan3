package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Briefadres;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Briefadres entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BriefadresRepository extends JpaRepository<Briefadres, Long> {}
