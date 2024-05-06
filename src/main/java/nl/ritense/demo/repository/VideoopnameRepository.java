package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Videoopname;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Videoopname entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VideoopnameRepository extends JpaRepository<Videoopname, Long> {}
