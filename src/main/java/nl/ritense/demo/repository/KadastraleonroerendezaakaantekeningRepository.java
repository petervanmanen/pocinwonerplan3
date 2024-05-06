package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Kadastraleonroerendezaakaantekening;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Kadastraleonroerendezaakaantekening entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KadastraleonroerendezaakaantekeningRepository extends JpaRepository<Kadastraleonroerendezaakaantekening, Long> {}
