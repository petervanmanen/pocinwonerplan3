package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Taalniveau;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Taalniveau entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaalniveauRepository extends JpaRepository<Taalniveau, Long> {}
