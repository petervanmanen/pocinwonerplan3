package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Verkeerstelling;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Verkeerstelling entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VerkeerstellingRepository extends JpaRepository<Verkeerstelling, Long> {}
