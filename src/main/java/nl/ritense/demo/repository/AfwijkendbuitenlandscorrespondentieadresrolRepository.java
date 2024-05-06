package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Afwijkendbuitenlandscorrespondentieadresrol;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Afwijkendbuitenlandscorrespondentieadresrol entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AfwijkendbuitenlandscorrespondentieadresrolRepository
    extends JpaRepository<Afwijkendbuitenlandscorrespondentieadresrol, Long> {}
