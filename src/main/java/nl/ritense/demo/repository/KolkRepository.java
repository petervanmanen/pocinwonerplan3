package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Kolk;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Kolk entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KolkRepository extends JpaRepository<Kolk, Long> {}
