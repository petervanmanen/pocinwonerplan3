package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Domeinoftaakveld;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Domeinoftaakveld entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DomeinoftaakveldRepository extends JpaRepository<Domeinoftaakveld, Long> {}
