package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Classh;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Classh entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClasshRepository extends JpaRepository<Classh, Long> {}
