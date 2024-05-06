package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Huishouden;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Huishouden entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HuishoudenRepository extends JpaRepository<Huishouden, Long> {}
