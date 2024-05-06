package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Adresseerbaarobject;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Adresseerbaarobject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdresseerbaarobjectRepository extends JpaRepository<Adresseerbaarobject, Long> {}
