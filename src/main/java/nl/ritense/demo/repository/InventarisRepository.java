package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Inventaris;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Inventaris entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InventarisRepository extends JpaRepository<Inventaris, Long> {}
