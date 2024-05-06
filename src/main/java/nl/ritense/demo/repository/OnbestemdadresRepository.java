package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Onbestemdadres;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Onbestemdadres entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OnbestemdadresRepository extends JpaRepository<Onbestemdadres, Long> {}
