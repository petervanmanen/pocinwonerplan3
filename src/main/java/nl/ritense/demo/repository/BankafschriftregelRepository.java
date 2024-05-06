package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Bankafschriftregel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Bankafschriftregel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BankafschriftregelRepository extends JpaRepository<Bankafschriftregel, Long> {}
