package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Leidingelement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Leidingelement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeidingelementRepository extends JpaRepository<Leidingelement, Long> {}
