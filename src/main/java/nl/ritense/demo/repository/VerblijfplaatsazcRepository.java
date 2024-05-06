package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Verblijfplaatsazc;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Verblijfplaatsazc entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VerblijfplaatsazcRepository extends JpaRepository<Verblijfplaatsazc, Long> {}
