package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Doelgroep;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Doelgroep entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DoelgroepRepository extends JpaRepository<Doelgroep, Long> {}
