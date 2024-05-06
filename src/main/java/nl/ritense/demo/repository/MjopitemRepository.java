package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Mjopitem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Mjopitem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MjopitemRepository extends JpaRepository<Mjopitem, Long> {}
