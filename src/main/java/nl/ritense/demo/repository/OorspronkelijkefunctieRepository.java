package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Oorspronkelijkefunctie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Oorspronkelijkefunctie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OorspronkelijkefunctieRepository extends JpaRepository<Oorspronkelijkefunctie, Long> {}
