package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Vastgoedcontract;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Vastgoedcontract entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VastgoedcontractRepository extends JpaRepository<Vastgoedcontract, Long> {}
