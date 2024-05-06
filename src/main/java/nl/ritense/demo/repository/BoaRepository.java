package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Boa;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Boa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BoaRepository extends JpaRepository<Boa, Long> {}
