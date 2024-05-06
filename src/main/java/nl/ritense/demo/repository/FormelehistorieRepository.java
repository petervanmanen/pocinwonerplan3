package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Formelehistorie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Formelehistorie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FormelehistorieRepository extends JpaRepository<Formelehistorie, Long> {}
