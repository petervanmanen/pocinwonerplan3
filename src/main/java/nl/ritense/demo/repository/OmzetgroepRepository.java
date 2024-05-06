package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Omzetgroep;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Omzetgroep entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OmzetgroepRepository extends JpaRepository<Omzetgroep, Long> {}
