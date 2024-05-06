package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Maatschappelijkeactiviteit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Maatschappelijkeactiviteit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MaatschappelijkeactiviteitRepository extends JpaRepository<Maatschappelijkeactiviteit, Long> {}
