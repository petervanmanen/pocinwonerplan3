package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Kwaliteitskenmerken;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Kwaliteitskenmerken entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KwaliteitskenmerkenRepository extends JpaRepository<Kwaliteitskenmerken, Long> {}
