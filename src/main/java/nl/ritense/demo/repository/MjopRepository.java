package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Mjop;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Mjop entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MjopRepository extends JpaRepository<Mjop, Long> {}
