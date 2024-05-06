package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Stadspas;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Stadspas entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StadspasRepository extends JpaRepository<Stadspas, Long> {}
