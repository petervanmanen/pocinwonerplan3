package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Appartementsrecht;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Appartementsrecht entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppartementsrechtRepository extends JpaRepository<Appartementsrecht, Long> {}
