package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Cmdbitem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Cmdbitem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CmdbitemRepository extends JpaRepository<Cmdbitem, Long> {}
