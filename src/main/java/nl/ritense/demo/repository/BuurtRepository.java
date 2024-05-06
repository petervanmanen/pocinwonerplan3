package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Buurt;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Buurt entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BuurtRepository extends JpaRepository<Buurt, Long> {}
