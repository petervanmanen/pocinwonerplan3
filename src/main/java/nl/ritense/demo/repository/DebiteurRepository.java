package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Debiteur;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Debiteur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DebiteurRepository extends JpaRepository<Debiteur, Long> {}
