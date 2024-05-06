package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Aanvraag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Aanvraag entity.
 *
 * When extending this class, extend AanvraagRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface AanvraagRepository extends AanvraagRepositoryWithBagRelationships, JpaRepository<Aanvraag, Long> {
    default Optional<Aanvraag> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<Aanvraag> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<Aanvraag> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
