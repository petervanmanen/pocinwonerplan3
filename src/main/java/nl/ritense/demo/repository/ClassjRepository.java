package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Classj;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Classj entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClassjRepository extends JpaRepository<Classj, Long> {}
