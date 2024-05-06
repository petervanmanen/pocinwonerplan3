package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Offerteaanvraag;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Offerteaanvraag entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OfferteaanvraagRepository extends JpaRepository<Offerteaanvraag, Long> {}
