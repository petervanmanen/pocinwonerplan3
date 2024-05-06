package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Stemming;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Stemming entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StemmingRepository extends JpaRepository<Stemming, Long> {}
