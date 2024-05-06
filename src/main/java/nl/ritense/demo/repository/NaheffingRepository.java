package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Naheffing;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Naheffing entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NaheffingRepository extends JpaRepository<Naheffing, Long> {}
