package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Beheerobject;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Beheerobject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BeheerobjectRepository extends JpaRepository<Beheerobject, Long> {}
