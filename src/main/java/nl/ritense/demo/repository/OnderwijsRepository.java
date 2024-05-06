package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Onderwijs;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Onderwijs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OnderwijsRepository extends JpaRepository<Onderwijs, Long> {}
