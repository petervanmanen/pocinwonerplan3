package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Subproducttype;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Subproducttype entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubproducttypeRepository extends JpaRepository<Subproducttype, Long> {}
