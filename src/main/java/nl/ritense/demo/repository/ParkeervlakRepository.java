package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Parkeervlak;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Parkeervlak entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParkeervlakRepository extends JpaRepository<Parkeervlak, Long> {}
