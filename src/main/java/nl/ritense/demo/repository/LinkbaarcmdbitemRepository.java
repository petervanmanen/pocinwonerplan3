package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Linkbaarcmdbitem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Linkbaarcmdbitem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LinkbaarcmdbitemRepository extends JpaRepository<Linkbaarcmdbitem, Long> {}
