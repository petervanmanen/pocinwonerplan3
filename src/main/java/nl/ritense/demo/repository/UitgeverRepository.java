package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Uitgever;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Uitgever entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UitgeverRepository extends JpaRepository<Uitgever, Long> {}
