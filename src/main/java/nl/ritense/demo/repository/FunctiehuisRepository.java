package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Functiehuis;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Functiehuis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FunctiehuisRepository extends JpaRepository<Functiehuis, Long> {}
