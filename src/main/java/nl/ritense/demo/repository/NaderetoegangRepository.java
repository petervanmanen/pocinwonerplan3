package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Naderetoegang;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Naderetoegang entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NaderetoegangRepository extends JpaRepository<Naderetoegang, Long> {}
