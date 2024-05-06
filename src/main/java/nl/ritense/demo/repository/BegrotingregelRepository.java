package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Begrotingregel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Begrotingregel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BegrotingregelRepository extends JpaRepository<Begrotingregel, Long> {}
