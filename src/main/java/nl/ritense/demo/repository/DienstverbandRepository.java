package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Dienstverband;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Dienstverband entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DienstverbandRepository extends JpaRepository<Dienstverband, Long> {}
