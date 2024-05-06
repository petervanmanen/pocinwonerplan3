package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Locatiekadastraleonroerendezaak;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Locatiekadastraleonroerendezaak entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocatiekadastraleonroerendezaakRepository extends JpaRepository<Locatiekadastraleonroerendezaak, Long> {}
