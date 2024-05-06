package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Boring;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Boring entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BoringRepository extends JpaRepository<Boring, Long> {}
