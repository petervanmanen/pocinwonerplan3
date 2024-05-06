package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Grondbeheerder;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Grondbeheerder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GrondbeheerderRepository extends JpaRepository<Grondbeheerder, Long> {}
