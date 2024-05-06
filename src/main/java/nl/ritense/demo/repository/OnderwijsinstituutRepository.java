package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Onderwijsinstituut;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Onderwijsinstituut entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OnderwijsinstituutRepository extends JpaRepository<Onderwijsinstituut, Long> {}
