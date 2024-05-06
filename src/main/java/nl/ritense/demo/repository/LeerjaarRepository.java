package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Leerjaar;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Leerjaar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeerjaarRepository extends JpaRepository<Leerjaar, Long> {}
