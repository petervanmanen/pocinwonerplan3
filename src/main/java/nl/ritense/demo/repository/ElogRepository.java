package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Elog;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Elog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ElogRepository extends JpaRepository<Elog, Long> {}
