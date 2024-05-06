package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Geboorteingeschrevenpersoon;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Geboorteingeschrevenpersoon entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GeboorteingeschrevenpersoonRepository extends JpaRepository<Geboorteingeschrevenpersoon, Long> {}
