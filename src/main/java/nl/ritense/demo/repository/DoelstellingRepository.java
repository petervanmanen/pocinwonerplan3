package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Doelstelling;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Doelstelling entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DoelstellingRepository extends JpaRepository<Doelstelling, Long> {}
