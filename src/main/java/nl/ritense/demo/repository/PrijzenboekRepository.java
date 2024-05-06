package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Prijzenboek;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Prijzenboek entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrijzenboekRepository extends JpaRepository<Prijzenboek, Long> {}
