package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Productofdienst;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Productofdienst entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductofdienstRepository extends JpaRepository<Productofdienst, Long> {}
