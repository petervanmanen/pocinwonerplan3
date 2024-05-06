package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Vaartuig;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Vaartuig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VaartuigRepository extends JpaRepository<Vaartuig, Long> {}
