package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Bord;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Bord entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BordRepository extends JpaRepository<Bord, Long> {}
