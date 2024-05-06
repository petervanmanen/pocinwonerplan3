package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Tentoonstelling;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Tentoonstelling entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TentoonstellingRepository extends JpaRepository<Tentoonstelling, Long> {}
