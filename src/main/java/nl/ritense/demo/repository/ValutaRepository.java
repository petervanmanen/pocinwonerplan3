package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Valuta;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Valuta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ValutaRepository extends JpaRepository<Valuta, Long> {}
