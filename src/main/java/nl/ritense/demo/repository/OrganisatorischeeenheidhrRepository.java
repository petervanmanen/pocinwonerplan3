package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Organisatorischeeenheidhr;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Organisatorischeeenheidhr entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrganisatorischeeenheidhrRepository extends JpaRepository<Organisatorischeeenheidhr, Long> {}
