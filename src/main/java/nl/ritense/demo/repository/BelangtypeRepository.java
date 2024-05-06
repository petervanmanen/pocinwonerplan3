package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Belangtype;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Belangtype entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BelangtypeRepository extends JpaRepository<Belangtype, Long> {}
