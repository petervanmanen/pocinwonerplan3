package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Uitvoerdergraafwerkzaamheden;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Uitvoerdergraafwerkzaamheden entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UitvoerdergraafwerkzaamhedenRepository extends JpaRepository<Uitvoerdergraafwerkzaamheden, Long> {}
