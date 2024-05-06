package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Subsidiecomponent;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Subsidiecomponent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubsidiecomponentRepository extends JpaRepository<Subsidiecomponent, Long> {}
