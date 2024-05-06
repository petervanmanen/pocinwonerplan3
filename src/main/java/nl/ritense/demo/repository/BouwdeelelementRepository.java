package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Bouwdeelelement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Bouwdeelelement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BouwdeelelementRepository extends JpaRepository<Bouwdeelelement, Long> {}
