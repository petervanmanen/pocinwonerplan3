package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Agendapunt;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Agendapunt entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AgendapuntRepository extends JpaRepository<Agendapunt, Long> {}
