package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Verlengingzaak;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Verlengingzaak entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VerlengingzaakRepository extends JpaRepository<Verlengingzaak, Long> {}
