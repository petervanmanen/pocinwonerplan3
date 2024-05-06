package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Naamgebruiknatuurlijkpersoon;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Naamgebruiknatuurlijkpersoon entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NaamgebruiknatuurlijkpersoonRepository extends JpaRepository<Naamgebruiknatuurlijkpersoon, Long> {}
