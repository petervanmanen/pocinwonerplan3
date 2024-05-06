package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Documenttype;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Documenttype entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumenttypeRepository extends JpaRepository<Documenttype, Long> {}
