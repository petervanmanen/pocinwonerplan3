package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Overigeadresseerbaarobjectaanduiding;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Overigeadresseerbaarobjectaanduiding entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OverigeadresseerbaarobjectaanduidingRepository extends JpaRepository<Overigeadresseerbaarobjectaanduiding, Long> {}
