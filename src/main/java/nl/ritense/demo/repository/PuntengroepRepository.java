package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Puntengroep;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Puntengroep entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PuntengroepRepository extends JpaRepository<Puntengroep, Long> {}
