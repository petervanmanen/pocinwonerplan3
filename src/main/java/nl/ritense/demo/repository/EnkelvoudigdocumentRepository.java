package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Enkelvoudigdocument;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Enkelvoudigdocument entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EnkelvoudigdocumentRepository extends JpaRepository<Enkelvoudigdocument, Long> {}
