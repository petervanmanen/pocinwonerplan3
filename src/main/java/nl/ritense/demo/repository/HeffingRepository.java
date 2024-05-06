package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Heffing;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Heffing entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HeffingRepository extends JpaRepository<Heffing, Long> {}
