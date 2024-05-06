package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Subrekening;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Subrekening entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubrekeningRepository extends JpaRepository<Subrekening, Long> {}
