package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Adresbuitenland;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Adresbuitenland entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdresbuitenlandRepository extends JpaRepository<Adresbuitenland, Long> {}
