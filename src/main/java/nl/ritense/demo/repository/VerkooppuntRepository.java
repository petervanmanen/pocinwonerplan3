package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Verkooppunt;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Verkooppunt entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VerkooppuntRepository extends JpaRepository<Verkooppunt, Long> {}
