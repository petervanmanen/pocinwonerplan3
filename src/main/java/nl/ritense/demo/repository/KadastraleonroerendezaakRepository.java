package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Kadastraleonroerendezaak;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Kadastraleonroerendezaak entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KadastraleonroerendezaakRepository extends JpaRepository<Kadastraleonroerendezaak, Long> {}
