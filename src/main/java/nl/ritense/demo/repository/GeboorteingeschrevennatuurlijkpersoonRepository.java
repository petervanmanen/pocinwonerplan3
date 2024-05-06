package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Geboorteingeschrevennatuurlijkpersoon;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Geboorteingeschrevennatuurlijkpersoon entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GeboorteingeschrevennatuurlijkpersoonRepository extends JpaRepository<Geboorteingeschrevennatuurlijkpersoon, Long> {}
