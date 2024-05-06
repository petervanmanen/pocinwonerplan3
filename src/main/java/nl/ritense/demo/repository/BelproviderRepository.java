package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Belprovider;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Belprovider entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BelproviderRepository extends JpaRepository<Belprovider, Long> {}
