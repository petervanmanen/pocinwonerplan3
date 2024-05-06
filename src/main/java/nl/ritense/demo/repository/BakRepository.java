package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Bak;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Bak entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BakRepository extends JpaRepository<Bak, Long> {}
