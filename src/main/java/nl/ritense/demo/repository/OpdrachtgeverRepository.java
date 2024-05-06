package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Opdrachtgever;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Opdrachtgever entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OpdrachtgeverRepository extends JpaRepository<Opdrachtgever, Long> {}
