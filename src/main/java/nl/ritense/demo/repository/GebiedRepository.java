package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Gebied;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Gebied entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GebiedRepository extends JpaRepository<Gebied, Long> {}
