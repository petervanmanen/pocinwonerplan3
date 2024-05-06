package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Beperkingsgebied;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Beperkingsgebied entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BeperkingsgebiedRepository extends JpaRepository<Beperkingsgebied, Long> {}
