package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Handelsnamenmaatschappelijkeactiviteit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Handelsnamenmaatschappelijkeactiviteit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HandelsnamenmaatschappelijkeactiviteitRepository extends JpaRepository<Handelsnamenmaatschappelijkeactiviteit, Long> {}
