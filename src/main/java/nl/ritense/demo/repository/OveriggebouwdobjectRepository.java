package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Overiggebouwdobject;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Overiggebouwdobject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OveriggebouwdobjectRepository extends JpaRepository<Overiggebouwdobject, Long> {}
