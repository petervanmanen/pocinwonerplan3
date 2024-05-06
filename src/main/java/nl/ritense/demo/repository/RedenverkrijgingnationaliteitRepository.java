package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Redenverkrijgingnationaliteit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Redenverkrijgingnationaliteit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RedenverkrijgingnationaliteitRepository extends JpaRepository<Redenverkrijgingnationaliteit, Long> {}
