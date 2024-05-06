package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Kunstwerkdeel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Kunstwerkdeel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KunstwerkdeelRepository extends JpaRepository<Kunstwerkdeel, Long> {}
