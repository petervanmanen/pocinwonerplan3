package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Bankafschrift;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Bankafschrift entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BankafschriftRepository extends JpaRepository<Bankafschrift, Long> {}
