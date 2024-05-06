package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Nietnatuurlijkpersoon;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Nietnatuurlijkpersoon entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NietnatuurlijkpersoonRepository extends JpaRepository<Nietnatuurlijkpersoon, Long> {}
