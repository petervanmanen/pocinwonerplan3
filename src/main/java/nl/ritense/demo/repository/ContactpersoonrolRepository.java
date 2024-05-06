package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Contactpersoonrol;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Contactpersoonrol entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContactpersoonrolRepository extends JpaRepository<Contactpersoonrol, Long> {}
