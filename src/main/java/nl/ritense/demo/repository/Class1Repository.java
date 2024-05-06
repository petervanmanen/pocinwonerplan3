package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Class1;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Class1 entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Class1Repository extends JpaRepository<Class1, Long> {}
