package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Formulierinhuur;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Formulierinhuur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FormulierinhuurRepository extends JpaRepository<Formulierinhuur, Long> {}
