package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Externebron;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Externebron entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExternebronRepository extends JpaRepository<Externebron, Long> {}
