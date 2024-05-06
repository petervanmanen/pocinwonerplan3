package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Vondst;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Vondst entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VondstRepository extends JpaRepository<Vondst, Long> {}
