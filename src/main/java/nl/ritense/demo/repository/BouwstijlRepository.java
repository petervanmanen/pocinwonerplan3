package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Bouwstijl;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Bouwstijl entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BouwstijlRepository extends JpaRepository<Bouwstijl, Long> {}
