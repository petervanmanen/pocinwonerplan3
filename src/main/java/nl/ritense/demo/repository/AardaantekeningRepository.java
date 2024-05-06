package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Aardaantekening;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Aardaantekening entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AardaantekeningRepository extends JpaRepository<Aardaantekening, Long> {}
