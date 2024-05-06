package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Classf;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Classf entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClassfRepository extends JpaRepository<Classf, Long> {}
