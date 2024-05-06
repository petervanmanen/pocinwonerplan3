package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Database;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Database entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DatabaseRepository extends JpaRepository<Database, Long> {}
