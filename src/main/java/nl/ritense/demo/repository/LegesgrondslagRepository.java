package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Legesgrondslag;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Legesgrondslag entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LegesgrondslagRepository extends JpaRepository<Legesgrondslag, Long> {}
