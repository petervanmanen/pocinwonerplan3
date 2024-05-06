package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Gebiedsaanwijzing;
import org.springframework.data.domain.Page;

public interface GebiedsaanwijzingRepositoryWithBagRelationships {
    Optional<Gebiedsaanwijzing> fetchBagRelationships(Optional<Gebiedsaanwijzing> gebiedsaanwijzing);

    List<Gebiedsaanwijzing> fetchBagRelationships(List<Gebiedsaanwijzing> gebiedsaanwijzings);

    Page<Gebiedsaanwijzing> fetchBagRelationships(Page<Gebiedsaanwijzing> gebiedsaanwijzings);
}
