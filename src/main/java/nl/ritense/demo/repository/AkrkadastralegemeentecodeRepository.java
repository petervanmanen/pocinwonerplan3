package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Akrkadastralegemeentecode;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Akrkadastralegemeentecode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AkrkadastralegemeentecodeRepository extends JpaRepository<Akrkadastralegemeentecode, Long> {}
