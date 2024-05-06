package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Heffinggrondslag;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Heffinggrondslag entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HeffinggrondslagRepository extends JpaRepository<Heffinggrondslag, Long> {}
