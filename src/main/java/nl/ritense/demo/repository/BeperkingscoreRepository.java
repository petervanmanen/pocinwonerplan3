package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Beperkingscore;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Beperkingscore entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BeperkingscoreRepository extends JpaRepository<Beperkingscore, Long> {}
