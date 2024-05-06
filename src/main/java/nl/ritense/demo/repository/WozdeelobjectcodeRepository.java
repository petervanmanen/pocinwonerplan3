package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Wozdeelobjectcode;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Wozdeelobjectcode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WozdeelobjectcodeRepository extends JpaRepository<Wozdeelobjectcode, Long> {}
