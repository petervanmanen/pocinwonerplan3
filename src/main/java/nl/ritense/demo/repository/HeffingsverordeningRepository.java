package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Heffingsverordening;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Heffingsverordening entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HeffingsverordeningRepository extends JpaRepository<Heffingsverordening, Long> {}
