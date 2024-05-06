package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Producttype;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Producttype entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProducttypeRepository extends JpaRepository<Producttype, Long> {}
