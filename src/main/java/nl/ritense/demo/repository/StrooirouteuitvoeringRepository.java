package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Strooirouteuitvoering;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Strooirouteuitvoering entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StrooirouteuitvoeringRepository extends JpaRepository<Strooirouteuitvoering, Long> {}
