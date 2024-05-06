package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Vrijstelling;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Vrijstelling entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VrijstellingRepository extends JpaRepository<Vrijstelling, Long> {}
