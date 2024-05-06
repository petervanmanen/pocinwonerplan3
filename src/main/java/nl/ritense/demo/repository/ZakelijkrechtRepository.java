package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Zakelijkrecht;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Zakelijkrecht entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ZakelijkrechtRepository extends JpaRepository<Zakelijkrecht, Long> {}
