package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Toewijzing;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Toewijzing entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ToewijzingRepository extends JpaRepository<Toewijzing, Long> {}
