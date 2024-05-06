package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Bouwwerk;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Bouwwerk entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BouwwerkRepository extends JpaRepository<Bouwwerk, Long> {}
