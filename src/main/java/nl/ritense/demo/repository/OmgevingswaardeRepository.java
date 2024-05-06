package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Omgevingswaarde;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Omgevingswaarde entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OmgevingswaardeRepository extends JpaRepository<Omgevingswaarde, Long> {}
