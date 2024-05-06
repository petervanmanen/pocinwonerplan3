package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Adresaanduiding;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Adresaanduiding entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdresaanduidingRepository extends JpaRepository<Adresaanduiding, Long> {}
