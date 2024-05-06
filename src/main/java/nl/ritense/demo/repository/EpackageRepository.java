package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Epackage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Epackage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EpackageRepository extends JpaRepository<Epackage, Long> {}
