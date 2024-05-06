package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Migratieingeschrevennatuurlijkpersoon;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Migratieingeschrevennatuurlijkpersoon entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MigratieingeschrevennatuurlijkpersoonRepository extends JpaRepository<Migratieingeschrevennatuurlijkpersoon, Long> {}
