package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SluitingofaangaanhuwelijkofgeregistreerdpartnerschapRepository
    extends JpaRepository<Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap, Long> {}
