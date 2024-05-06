package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Verlichtingsobject;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Verlichtingsobject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VerlichtingsobjectRepository extends JpaRepository<Verlichtingsobject, Long> {}
