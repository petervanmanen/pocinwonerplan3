package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Classd;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Classd entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClassdRepository extends JpaRepository<Classd, Long> {}
