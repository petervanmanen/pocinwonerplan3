package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Geluidsscherm;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Geluidsscherm entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GeluidsschermRepository extends JpaRepository<Geluidsscherm, Long> {}
