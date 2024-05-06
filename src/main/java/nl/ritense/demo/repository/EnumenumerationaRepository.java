package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Enumenumerationa;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Enumenumerationa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EnumenumerationaRepository extends JpaRepository<Enumenumerationa, Integer> {}
