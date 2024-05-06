package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Vlak;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Vlak entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VlakRepository extends JpaRepository<Vlak, Long> {}
