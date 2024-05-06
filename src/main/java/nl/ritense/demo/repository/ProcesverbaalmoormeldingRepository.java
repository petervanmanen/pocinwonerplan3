package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Procesverbaalmoormelding;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Procesverbaalmoormelding entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcesverbaalmoormeldingRepository extends JpaRepository<Procesverbaalmoormelding, Long> {}
