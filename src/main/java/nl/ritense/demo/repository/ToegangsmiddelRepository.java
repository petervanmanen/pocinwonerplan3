package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Toegangsmiddel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Toegangsmiddel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ToegangsmiddelRepository extends JpaRepository<Toegangsmiddel, Long> {}
