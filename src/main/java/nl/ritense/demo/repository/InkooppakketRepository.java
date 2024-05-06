package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Inkooppakket;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Inkooppakket entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InkooppakketRepository extends JpaRepository<Inkooppakket, Long> {}
