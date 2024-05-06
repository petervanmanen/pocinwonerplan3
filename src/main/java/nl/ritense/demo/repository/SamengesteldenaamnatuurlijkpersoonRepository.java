package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Samengesteldenaamnatuurlijkpersoon;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Samengesteldenaamnatuurlijkpersoon entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SamengesteldenaamnatuurlijkpersoonRepository extends JpaRepository<Samengesteldenaamnatuurlijkpersoon, Long> {}
