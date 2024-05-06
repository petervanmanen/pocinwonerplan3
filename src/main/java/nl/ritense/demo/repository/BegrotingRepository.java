package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Begroting;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Begroting entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BegrotingRepository extends JpaRepository<Begroting, Long> {}
