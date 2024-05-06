package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Telefoniegegevens;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Telefoniegegevens entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TelefoniegegevensRepository extends JpaRepository<Telefoniegegevens, Long> {}
