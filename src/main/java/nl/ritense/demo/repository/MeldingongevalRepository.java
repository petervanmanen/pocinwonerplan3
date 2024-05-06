package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Meldingongeval;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Meldingongeval entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MeldingongevalRepository extends JpaRepository<Meldingongeval, Long> {}
