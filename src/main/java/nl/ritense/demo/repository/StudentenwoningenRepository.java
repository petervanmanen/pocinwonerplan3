package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Studentenwoningen;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Studentenwoningen entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentenwoningenRepository extends JpaRepository<Studentenwoningen, Long> {}
