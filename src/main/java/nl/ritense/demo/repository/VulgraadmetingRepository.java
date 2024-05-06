package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Vulgraadmeting;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Vulgraadmeting entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VulgraadmetingRepository extends JpaRepository<Vulgraadmeting, Long> {}
