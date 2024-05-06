package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Overbruggingsobject;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Overbruggingsobject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OverbruggingsobjectRepository extends JpaRepository<Overbruggingsobject, Long> {}
