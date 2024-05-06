package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Mulderfeit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Mulderfeit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MulderfeitRepository extends JpaRepository<Mulderfeit, Long> {}
