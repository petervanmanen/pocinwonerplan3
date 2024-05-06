package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Winkelvoorraaditem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Winkelvoorraaditem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WinkelvoorraaditemRepository extends JpaRepository<Winkelvoorraaditem, Long> {}
