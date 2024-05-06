package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Nertwerkcomponent;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Nertwerkcomponent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NertwerkcomponentRepository extends JpaRepository<Nertwerkcomponent, Long> {}
