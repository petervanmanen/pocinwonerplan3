package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Telefoononderwerp;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Telefoononderwerp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TelefoononderwerpRepository extends JpaRepository<Telefoononderwerp, Long> {}
