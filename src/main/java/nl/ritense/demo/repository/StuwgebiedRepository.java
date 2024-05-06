package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Stuwgebied;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Stuwgebied entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StuwgebiedRepository extends JpaRepository<Stuwgebied, Long> {}
