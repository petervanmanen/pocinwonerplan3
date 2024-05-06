package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Winkelverkoopgroep;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Winkelverkoopgroep entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WinkelverkoopgroepRepository extends JpaRepository<Winkelverkoopgroep, Long> {}
