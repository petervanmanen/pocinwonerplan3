package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Historischpersoon;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Historischpersoon entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HistorischpersoonRepository extends JpaRepository<Historischpersoon, Long> {}
