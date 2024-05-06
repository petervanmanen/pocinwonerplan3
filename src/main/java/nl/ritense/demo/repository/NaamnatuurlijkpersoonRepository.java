package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Naamnatuurlijkpersoon;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Naamnatuurlijkpersoon entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NaamnatuurlijkpersoonRepository extends JpaRepository<Naamnatuurlijkpersoon, Long> {}
