package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Paal;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Paal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaalRepository extends JpaRepository<Paal, Long> {}
