package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Kadastraalperceel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Kadastraalperceel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KadastraalperceelRepository extends JpaRepository<Kadastraalperceel, Long> {}
