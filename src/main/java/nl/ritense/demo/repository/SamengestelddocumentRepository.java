package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Samengestelddocument;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Samengestelddocument entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SamengestelddocumentRepository extends JpaRepository<Samengestelddocument, Long> {}
