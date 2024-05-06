package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Gebiedsaanwijzing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Gebiedsaanwijzing entity.
 *
 * When extending this class, extend GebiedsaanwijzingRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface GebiedsaanwijzingRepository
    extends GebiedsaanwijzingRepositoryWithBagRelationships, JpaRepository<Gebiedsaanwijzing, Long> {
    default Optional<Gebiedsaanwijzing> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<Gebiedsaanwijzing> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<Gebiedsaanwijzing> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
