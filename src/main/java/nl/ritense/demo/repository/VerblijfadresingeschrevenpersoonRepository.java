package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Verblijfadresingeschrevenpersoon;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Verblijfadresingeschrevenpersoon entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VerblijfadresingeschrevenpersoonRepository extends JpaRepository<Verblijfadresingeschrevenpersoon, Long> {}
