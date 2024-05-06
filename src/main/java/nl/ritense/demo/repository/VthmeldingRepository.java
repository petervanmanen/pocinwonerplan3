package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Vthmelding;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Vthmelding entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VthmeldingRepository extends JpaRepository<Vthmelding, Long> {}
