package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Omgevingsvergunning;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Omgevingsvergunning entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OmgevingsvergunningRepository extends JpaRepository<Omgevingsvergunning, Long> {}
