package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Tenaamstelling;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Tenaamstelling entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TenaamstellingRepository extends JpaRepository<Tenaamstelling, Long> {}
