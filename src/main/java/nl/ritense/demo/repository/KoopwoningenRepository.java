package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Koopwoningen;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Koopwoningen entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KoopwoningenRepository extends JpaRepository<Koopwoningen, Long> {}
