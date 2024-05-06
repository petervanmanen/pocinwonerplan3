package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Lstclass1;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Lstclass1 entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Lstclass1Repository extends JpaRepository<Lstclass1, Long> {}
