package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Classi;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Classi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClassiRepository extends JpaRepository<Classi, Long> {}
