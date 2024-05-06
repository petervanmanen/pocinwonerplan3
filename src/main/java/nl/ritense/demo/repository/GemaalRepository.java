package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Gemaal;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Gemaal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GemaalRepository extends JpaRepository<Gemaal, Long> {}
