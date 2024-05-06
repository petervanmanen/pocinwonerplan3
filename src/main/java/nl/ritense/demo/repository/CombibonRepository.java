package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Combibon;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Combibon entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CombibonRepository extends JpaRepository<Combibon, Long> {}
