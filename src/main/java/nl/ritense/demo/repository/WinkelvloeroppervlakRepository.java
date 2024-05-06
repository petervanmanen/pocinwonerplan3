package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Winkelvloeroppervlak;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Winkelvloeroppervlak entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WinkelvloeroppervlakRepository extends JpaRepository<Winkelvloeroppervlak, Long> {}
