package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Hotelbezoek;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Hotelbezoek entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HotelbezoekRepository extends JpaRepository<Hotelbezoek, Long> {}
