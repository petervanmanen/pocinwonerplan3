package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Selectietabelaanbesteding;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Selectietabelaanbesteding entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SelectietabelaanbestedingRepository extends JpaRepository<Selectietabelaanbesteding, Long> {}
