package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Appartementsrechtsplitsing;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Appartementsrechtsplitsing entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppartementsrechtsplitsingRepository extends JpaRepository<Appartementsrechtsplitsing, Long> {}
