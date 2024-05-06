package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Gebouwinstallatie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Gebouwinstallatie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GebouwinstallatieRepository extends JpaRepository<Gebouwinstallatie, Long> {}
