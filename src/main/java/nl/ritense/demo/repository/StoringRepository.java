package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Storing;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Storing entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StoringRepository extends JpaRepository<Storing, Long> {}
