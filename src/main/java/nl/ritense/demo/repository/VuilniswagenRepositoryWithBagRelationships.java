package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Vuilniswagen;
import org.springframework.data.domain.Page;

public interface VuilniswagenRepositoryWithBagRelationships {
    Optional<Vuilniswagen> fetchBagRelationships(Optional<Vuilniswagen> vuilniswagen);

    List<Vuilniswagen> fetchBagRelationships(List<Vuilniswagen> vuilniswagens);

    Page<Vuilniswagen> fetchBagRelationships(Page<Vuilniswagen> vuilniswagens);
}
