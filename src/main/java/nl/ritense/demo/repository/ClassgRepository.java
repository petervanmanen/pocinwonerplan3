package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Classg;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Classg entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClassgRepository extends JpaRepository<Classg, Long> {}
