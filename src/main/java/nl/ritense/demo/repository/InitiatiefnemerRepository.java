package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Initiatiefnemer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Initiatiefnemer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InitiatiefnemerRepository extends JpaRepository<Initiatiefnemer, Long> {}
