package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Doelstellingsoort;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Doelstellingsoort entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DoelstellingsoortRepository extends JpaRepository<Doelstellingsoort, Long> {}
