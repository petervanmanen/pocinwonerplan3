package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Aommeldingwmojeugd;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Aommeldingwmojeugd entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AommeldingwmojeugdRepository extends JpaRepository<Aommeldingwmojeugd, Long> {}
