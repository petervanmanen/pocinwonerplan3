package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Gebruikerrol;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Gebruikerrol entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GebruikerrolRepository extends JpaRepository<Gebruikerrol, Long> {}
