package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Beperkingscoresoort;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Beperkingscoresoort entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BeperkingscoresoortRepository extends JpaRepository<Beperkingscoresoort, Long> {}
