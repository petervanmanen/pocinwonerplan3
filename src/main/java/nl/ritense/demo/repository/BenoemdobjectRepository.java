package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Benoemdobject;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Benoemdobject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BenoemdobjectRepository extends JpaRepository<Benoemdobject, Long> {}
