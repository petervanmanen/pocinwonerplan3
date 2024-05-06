package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Zelfredzaamheidmatrix;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Zelfredzaamheidmatrix entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ZelfredzaamheidmatrixRepository extends JpaRepository<Zelfredzaamheidmatrix, Long> {}
