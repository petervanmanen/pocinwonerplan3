package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Juridischeregel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Juridischeregel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JuridischeregelRepository extends JpaRepository<Juridischeregel, Long> {}
