package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Indieningsvereisten;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Indieningsvereisten entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IndieningsvereistenRepository extends JpaRepository<Indieningsvereisten, Long> {}
