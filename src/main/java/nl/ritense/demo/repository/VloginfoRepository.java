package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Vloginfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Vloginfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VloginfoRepository extends JpaRepository<Vloginfo, Long> {}
