package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Tegenprestatiehoogte;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Tegenprestatiehoogte entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TegenprestatiehoogteRepository extends JpaRepository<Tegenprestatiehoogte, Long> {}
