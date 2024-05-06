package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Onderwijsloopbaan;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Onderwijsloopbaan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OnderwijsloopbaanRepository extends JpaRepository<Onderwijsloopbaan, Long> {}
