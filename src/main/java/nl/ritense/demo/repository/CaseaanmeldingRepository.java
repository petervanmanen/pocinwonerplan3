package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Caseaanmelding;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Caseaanmelding entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CaseaanmeldingRepository extends JpaRepository<Caseaanmelding, Long> {}
