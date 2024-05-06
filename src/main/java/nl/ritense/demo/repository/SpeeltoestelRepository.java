package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Speeltoestel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Speeltoestel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpeeltoestelRepository extends JpaRepository<Speeltoestel, Long> {}
