package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Eigenaar;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Eigenaar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EigenaarRepository extends JpaRepository<Eigenaar, Long> {}
