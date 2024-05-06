package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Formatieplaats;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Formatieplaats entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FormatieplaatsRepository extends JpaRepository<Formatieplaats, Long> {}
