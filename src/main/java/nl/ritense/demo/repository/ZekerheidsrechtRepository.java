package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Zekerheidsrecht;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Zekerheidsrecht entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ZekerheidsrechtRepository extends JpaRepository<Zekerheidsrecht, Long> {}
