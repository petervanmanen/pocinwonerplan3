package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Zaaktype;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Zaaktype entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ZaaktypeRepository extends JpaRepository<Zaaktype, Long> {}
