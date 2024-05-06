package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Besluittype;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Besluittype entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BesluittypeRepository extends JpaRepository<Besluittype, Long> {}
