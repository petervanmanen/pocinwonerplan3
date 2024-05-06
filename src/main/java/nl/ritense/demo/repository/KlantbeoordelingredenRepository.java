package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Klantbeoordelingreden;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Klantbeoordelingreden entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KlantbeoordelingredenRepository extends JpaRepository<Klantbeoordelingreden, Long> {}
