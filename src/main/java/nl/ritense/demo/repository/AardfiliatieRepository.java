package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Aardfiliatie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Aardfiliatie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AardfiliatieRepository extends JpaRepository<Aardfiliatie, Long> {}
