package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Autoriteitafgiftenederlandsreisdocument;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Autoriteitafgiftenederlandsreisdocument entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AutoriteitafgiftenederlandsreisdocumentRepository extends JpaRepository<Autoriteitafgiftenederlandsreisdocument, Long> {}
