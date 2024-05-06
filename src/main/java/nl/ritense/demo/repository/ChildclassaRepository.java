package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Childclassa;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Childclassa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChildclassaRepository extends JpaRepository<Childclassa, Long> {}
