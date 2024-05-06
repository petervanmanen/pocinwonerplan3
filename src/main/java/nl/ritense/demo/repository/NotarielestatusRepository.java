package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Notarielestatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Notarielestatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NotarielestatusRepository extends JpaRepository<Notarielestatus, Long> {}
