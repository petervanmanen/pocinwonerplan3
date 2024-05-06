package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Leerplichtambtenaar;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Leerplichtambtenaar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeerplichtambtenaarRepository extends JpaRepository<Leerplichtambtenaar, Long> {}
