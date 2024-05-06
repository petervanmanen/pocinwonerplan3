package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Verhardingsobject;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Verhardingsobject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VerhardingsobjectRepository extends JpaRepository<Verhardingsobject, Long> {}
