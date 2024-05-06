package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Uitkeringsrun;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Uitkeringsrun entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UitkeringsrunRepository extends JpaRepository<Uitkeringsrun, Long> {}
