package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Prijsafspraak;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Prijsafspraak entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrijsafspraakRepository extends JpaRepository<Prijsafspraak, Long> {}
