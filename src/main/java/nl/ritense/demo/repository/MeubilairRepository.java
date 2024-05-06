package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Meubilair;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Meubilair entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MeubilairRepository extends JpaRepository<Meubilair, Long> {}
