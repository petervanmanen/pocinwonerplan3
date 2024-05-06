package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Verzoekomtoewijzing;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Verzoekomtoewijzing entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VerzoekomtoewijzingRepository extends JpaRepository<Verzoekomtoewijzing, Long> {}
