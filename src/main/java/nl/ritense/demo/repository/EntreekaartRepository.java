package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Entreekaart;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Entreekaart entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntreekaartRepository extends JpaRepository<Entreekaart, Long> {}
