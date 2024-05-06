package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Aantekening;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Aantekening entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AantekeningRepository extends JpaRepository<Aantekening, Long> {}
