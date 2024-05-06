package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Adresseerbaarobjectaanduiding;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Adresseerbaarobjectaanduiding entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdresseerbaarobjectaanduidingRepository extends JpaRepository<Adresseerbaarobjectaanduiding, Long> {}
