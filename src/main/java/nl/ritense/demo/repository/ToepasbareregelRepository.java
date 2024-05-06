package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Toepasbareregel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Toepasbareregel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ToepasbareregelRepository extends JpaRepository<Toepasbareregel, Long> {}
