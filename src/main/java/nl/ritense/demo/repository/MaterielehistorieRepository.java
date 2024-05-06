package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Materielehistorie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Materielehistorie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MaterielehistorieRepository extends JpaRepository<Materielehistorie, Long> {}
