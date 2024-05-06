package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Prijzenboekitem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Prijzenboekitem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrijzenboekitemRepository extends JpaRepository<Prijzenboekitem, Long> {}
