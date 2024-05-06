package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Bankrekening;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Bankrekening entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BankrekeningRepository extends JpaRepository<Bankrekening, Long> {}
