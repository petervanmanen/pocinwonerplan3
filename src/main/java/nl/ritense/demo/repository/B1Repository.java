package nl.ritense.demo.repository;

import nl.ritense.demo.domain.B1;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the B1 entity.
 */
@SuppressWarnings("unused")
@Repository
public interface B1Repository extends JpaRepository<B1, Long> {}
