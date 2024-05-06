package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Hardware;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Hardware entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HardwareRepository extends JpaRepository<Hardware, Long> {}
