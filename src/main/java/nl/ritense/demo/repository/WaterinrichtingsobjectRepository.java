package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Waterinrichtingsobject;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Waterinrichtingsobject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WaterinrichtingsobjectRepository extends JpaRepository<Waterinrichtingsobject, Long> {}
