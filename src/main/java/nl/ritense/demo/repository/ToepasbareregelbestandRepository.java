package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Toepasbareregelbestand;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Toepasbareregelbestand entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ToepasbareregelbestandRepository extends JpaRepository<Toepasbareregelbestand, Long> {}
