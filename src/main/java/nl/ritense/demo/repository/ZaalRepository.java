package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Zaal;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Zaal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ZaalRepository extends JpaRepository<Zaal, Long> {}
