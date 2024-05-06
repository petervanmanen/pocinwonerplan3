package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Flyover;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Flyover entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FlyoverRepository extends JpaRepository<Flyover, Long> {}
