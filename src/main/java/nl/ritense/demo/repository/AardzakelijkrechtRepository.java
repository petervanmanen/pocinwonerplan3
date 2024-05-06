package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Aardzakelijkrecht;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Aardzakelijkrecht entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AardzakelijkrechtRepository extends JpaRepository<Aardzakelijkrecht, Long> {}
