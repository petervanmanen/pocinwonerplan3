package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Waarneming;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Waarneming entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WaarnemingRepository extends JpaRepository<Waarneming, Long> {}
