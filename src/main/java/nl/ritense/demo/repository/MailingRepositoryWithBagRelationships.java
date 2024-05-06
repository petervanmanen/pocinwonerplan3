package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Mailing;
import org.springframework.data.domain.Page;

public interface MailingRepositoryWithBagRelationships {
    Optional<Mailing> fetchBagRelationships(Optional<Mailing> mailing);

    List<Mailing> fetchBagRelationships(List<Mailing> mailings);

    Page<Mailing> fetchBagRelationships(Page<Mailing> mailings);
}
