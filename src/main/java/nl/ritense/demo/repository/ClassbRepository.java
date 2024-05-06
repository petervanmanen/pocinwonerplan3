package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Classb;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Classb entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClassbRepository extends JpaRepository<Classb, Long> {}
