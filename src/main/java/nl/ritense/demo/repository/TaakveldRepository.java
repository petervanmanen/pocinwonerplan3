package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Taakveld;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Taakveld entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaakveldRepository extends JpaRepository<Taakveld, Long> {}
