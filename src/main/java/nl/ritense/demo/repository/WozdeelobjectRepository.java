package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Wozdeelobject;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Wozdeelobject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WozdeelobjectRepository extends JpaRepository<Wozdeelobject, Long> {}
