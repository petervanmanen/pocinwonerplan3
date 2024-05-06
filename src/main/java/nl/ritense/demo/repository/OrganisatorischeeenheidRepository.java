package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Organisatorischeeenheid;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Organisatorischeeenheid entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrganisatorischeeenheidRepository extends JpaRepository<Organisatorischeeenheid, Long> {}
