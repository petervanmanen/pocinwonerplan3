package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Werkorder;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Werkorder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WerkorderRepository extends JpaRepository<Werkorder, Long> {}
