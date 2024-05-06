package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Verblijfbuitenlandsubject;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Verblijfbuitenlandsubject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VerblijfbuitenlandsubjectRepository extends JpaRepository<Verblijfbuitenlandsubject, Long> {}
