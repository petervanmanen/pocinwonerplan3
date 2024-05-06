package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Natuurlijkpersoon;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Natuurlijkpersoon entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NatuurlijkpersoonRepository extends JpaRepository<Natuurlijkpersoon, Long> {}
