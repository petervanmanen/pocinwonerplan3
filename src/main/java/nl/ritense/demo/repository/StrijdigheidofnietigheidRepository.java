package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Strijdigheidofnietigheid;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Strijdigheidofnietigheid entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StrijdigheidofnietigheidRepository extends JpaRepository<Strijdigheidofnietigheid, Long> {}
