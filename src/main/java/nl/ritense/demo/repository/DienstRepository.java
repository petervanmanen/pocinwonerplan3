package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Dienst;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Dienst entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DienstRepository extends JpaRepository<Dienst, Long> {}
