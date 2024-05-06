package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Afvalbak;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Afvalbak entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AfvalbakRepository extends JpaRepository<Afvalbak, Long> {}
