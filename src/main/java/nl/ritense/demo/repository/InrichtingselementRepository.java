package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Inrichtingselement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Inrichtingselement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InrichtingselementRepository extends JpaRepository<Inrichtingselement, Long> {}
