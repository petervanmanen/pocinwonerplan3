package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Maatregelen;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Maatregelen entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MaatregelenRepository extends JpaRepository<Maatregelen, Long> {}
