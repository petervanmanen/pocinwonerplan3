package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Belanghebbende;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Belanghebbende entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BelanghebbendeRepository extends JpaRepository<Belanghebbende, Long> {}
