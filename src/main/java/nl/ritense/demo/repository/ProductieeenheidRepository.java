package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Productieeenheid;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Productieeenheid entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductieeenheidRepository extends JpaRepository<Productieeenheid, Long> {}
