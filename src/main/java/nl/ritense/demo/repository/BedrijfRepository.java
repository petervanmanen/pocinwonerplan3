package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Bedrijf;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Bedrijf entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BedrijfRepository extends JpaRepository<Bedrijf, Long> {}
