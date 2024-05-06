package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Leefgebied;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Leefgebied entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeefgebiedRepository extends JpaRepository<Leefgebied, Long> {}
