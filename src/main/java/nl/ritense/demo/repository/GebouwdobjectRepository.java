package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Gebouwdobject;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Gebouwdobject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GebouwdobjectRepository extends JpaRepository<Gebouwdobject, Long> {}
