package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Wozbelang;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Wozbelang entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WozbelangRepository extends JpaRepository<Wozbelang, Long> {}
