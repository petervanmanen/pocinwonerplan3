package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Knm;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Knm entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KnmRepository extends JpaRepository<Knm, Long> {}
