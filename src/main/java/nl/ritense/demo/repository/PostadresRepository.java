package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Postadres;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Postadres entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PostadresRepository extends JpaRepository<Postadres, Long> {}
