package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Koopsomkadastraleonroerendezaak;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Koopsomkadastraleonroerendezaak entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KoopsomkadastraleonroerendezaakRepository extends JpaRepository<Koopsomkadastraleonroerendezaak, Long> {}
