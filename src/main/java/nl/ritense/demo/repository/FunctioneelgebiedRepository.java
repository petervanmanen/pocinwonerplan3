package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Functioneelgebied;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Functioneelgebied entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FunctioneelgebiedRepository extends JpaRepository<Functioneelgebied, Long> {}
