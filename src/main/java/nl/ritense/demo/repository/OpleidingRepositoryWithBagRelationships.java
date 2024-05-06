package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Opleiding;
import org.springframework.data.domain.Page;

public interface OpleidingRepositoryWithBagRelationships {
    Optional<Opleiding> fetchBagRelationships(Optional<Opleiding> opleiding);

    List<Opleiding> fetchBagRelationships(List<Opleiding> opleidings);

    Page<Opleiding> fetchBagRelationships(Page<Opleiding> opleidings);
}
