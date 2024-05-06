package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Eobjecttypeg;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Eobjecttypeg entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EobjecttypegRepository extends JpaRepository<Eobjecttypeg, Long> {}
