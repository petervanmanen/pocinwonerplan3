package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Containertype;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Containertype entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContainertypeRepository extends JpaRepository<Containertype, Long> {}
