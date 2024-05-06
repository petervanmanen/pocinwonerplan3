package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Elogboek;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Elogboek entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ElogboekRepository extends JpaRepository<Elogboek, Long> {}
