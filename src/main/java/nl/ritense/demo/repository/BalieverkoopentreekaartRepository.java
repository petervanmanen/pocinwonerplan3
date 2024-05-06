package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Balieverkoopentreekaart;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Balieverkoopentreekaart entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BalieverkoopentreekaartRepository extends JpaRepository<Balieverkoopentreekaart, Long> {}
