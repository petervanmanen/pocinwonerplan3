package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Bouwtype;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Bouwtype entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BouwtypeRepository extends JpaRepository<Bouwtype, Long> {}
