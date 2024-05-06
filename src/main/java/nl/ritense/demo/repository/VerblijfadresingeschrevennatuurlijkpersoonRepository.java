package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Verblijfadresingeschrevennatuurlijkpersoon;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Verblijfadresingeschrevennatuurlijkpersoon entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VerblijfadresingeschrevennatuurlijkpersoonRepository
    extends JpaRepository<Verblijfadresingeschrevennatuurlijkpersoon, Long> {}
