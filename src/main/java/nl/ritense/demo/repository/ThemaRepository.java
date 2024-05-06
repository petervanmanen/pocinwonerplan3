package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Thema;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Thema entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ThemaRepository extends JpaRepository<Thema, Long> {}
