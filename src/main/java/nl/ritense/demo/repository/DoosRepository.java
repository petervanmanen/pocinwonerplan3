package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Doos;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Doos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DoosRepository extends JpaRepository<Doos, Long> {}
