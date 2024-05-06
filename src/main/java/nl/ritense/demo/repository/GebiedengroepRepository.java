package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Gebiedengroep;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Gebiedengroep entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GebiedengroepRepository extends JpaRepository<Gebiedengroep, Long> {}
