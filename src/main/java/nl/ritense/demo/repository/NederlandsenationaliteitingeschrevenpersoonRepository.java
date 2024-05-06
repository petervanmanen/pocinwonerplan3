package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Nederlandsenationaliteitingeschrevenpersoon;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Nederlandsenationaliteitingeschrevenpersoon entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NederlandsenationaliteitingeschrevenpersoonRepository
    extends JpaRepository<Nederlandsenationaliteitingeschrevenpersoon, Long> {}
