package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Sbiactiviteitvestiging;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Sbiactiviteitvestiging entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SbiactiviteitvestigingRepository extends JpaRepository<Sbiactiviteitvestiging, Long> {}
