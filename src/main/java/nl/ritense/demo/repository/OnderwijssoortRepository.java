package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Onderwijssoort;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Onderwijssoort entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OnderwijssoortRepository extends JpaRepository<Onderwijssoort, Long> {}
