package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Fietsregistratie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Fietsregistratie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FietsregistratieRepository extends JpaRepository<Fietsregistratie, Long> {}
