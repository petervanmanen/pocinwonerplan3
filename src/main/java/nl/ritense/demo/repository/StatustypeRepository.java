package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Statustype;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Statustype entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StatustypeRepository extends JpaRepository<Statustype, Long> {}
