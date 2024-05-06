package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Sbiactiviteit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Sbiactiviteit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SbiactiviteitRepository extends JpaRepository<Sbiactiviteit, Long> {}
