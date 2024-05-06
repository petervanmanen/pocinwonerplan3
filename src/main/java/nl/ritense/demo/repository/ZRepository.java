package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Z;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Z entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ZRepository extends JpaRepository<Z, Long> {}
