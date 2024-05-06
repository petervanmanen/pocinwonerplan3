package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Bemalingsgebied;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Bemalingsgebied entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BemalingsgebiedRepository extends JpaRepository<Bemalingsgebied, Long> {}
