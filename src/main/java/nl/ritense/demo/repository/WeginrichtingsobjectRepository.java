package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Weginrichtingsobject;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Weginrichtingsobject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WeginrichtingsobjectRepository extends JpaRepository<Weginrichtingsobject, Long> {}
