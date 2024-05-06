package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Eobjectclassificatie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Eobjectclassificatie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EobjectclassificatieRepository extends JpaRepository<Eobjectclassificatie, Long> {}
