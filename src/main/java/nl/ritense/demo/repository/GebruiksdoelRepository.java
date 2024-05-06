package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Gebruiksdoel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Gebruiksdoel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GebruiksdoelRepository extends JpaRepository<Gebruiksdoel, Long> {}
