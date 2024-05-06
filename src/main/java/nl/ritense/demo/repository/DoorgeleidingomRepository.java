package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Doorgeleidingom;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Doorgeleidingom entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DoorgeleidingomRepository extends JpaRepository<Doorgeleidingom, Long> {}
