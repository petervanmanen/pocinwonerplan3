package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Offerte;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Offerte entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OfferteRepository extends JpaRepository<Offerte, Long> {}
