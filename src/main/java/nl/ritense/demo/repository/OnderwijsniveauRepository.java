package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Onderwijsniveau;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Onderwijsniveau entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OnderwijsniveauRepository extends JpaRepository<Onderwijsniveau, Long> {}
