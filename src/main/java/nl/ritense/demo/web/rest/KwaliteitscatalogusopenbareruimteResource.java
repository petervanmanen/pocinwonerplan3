package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Kwaliteitscatalogusopenbareruimte;
import nl.ritense.demo.repository.KwaliteitscatalogusopenbareruimteRepository;
import nl.ritense.demo.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link nl.ritense.demo.domain.Kwaliteitscatalogusopenbareruimte}.
 */
@RestController
@RequestMapping("/api/kwaliteitscatalogusopenbareruimtes")
@Transactional
public class KwaliteitscatalogusopenbareruimteResource {

    private final Logger log = LoggerFactory.getLogger(KwaliteitscatalogusopenbareruimteResource.class);

    private static final String ENTITY_NAME = "kwaliteitscatalogusopenbareruimte";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KwaliteitscatalogusopenbareruimteRepository kwaliteitscatalogusopenbareruimteRepository;

    public KwaliteitscatalogusopenbareruimteResource(
        KwaliteitscatalogusopenbareruimteRepository kwaliteitscatalogusopenbareruimteRepository
    ) {
        this.kwaliteitscatalogusopenbareruimteRepository = kwaliteitscatalogusopenbareruimteRepository;
    }

    /**
     * {@code POST  /kwaliteitscatalogusopenbareruimtes} : Create a new kwaliteitscatalogusopenbareruimte.
     *
     * @param kwaliteitscatalogusopenbareruimte the kwaliteitscatalogusopenbareruimte to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kwaliteitscatalogusopenbareruimte, or with status {@code 400 (Bad Request)} if the kwaliteitscatalogusopenbareruimte has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Kwaliteitscatalogusopenbareruimte> createKwaliteitscatalogusopenbareruimte(
        @RequestBody Kwaliteitscatalogusopenbareruimte kwaliteitscatalogusopenbareruimte
    ) throws URISyntaxException {
        log.debug("REST request to save Kwaliteitscatalogusopenbareruimte : {}", kwaliteitscatalogusopenbareruimte);
        if (kwaliteitscatalogusopenbareruimte.getId() != null) {
            throw new BadRequestAlertException(
                "A new kwaliteitscatalogusopenbareruimte cannot already have an ID",
                ENTITY_NAME,
                "idexists"
            );
        }
        kwaliteitscatalogusopenbareruimte = kwaliteitscatalogusopenbareruimteRepository.save(kwaliteitscatalogusopenbareruimte);
        return ResponseEntity.created(new URI("/api/kwaliteitscatalogusopenbareruimtes/" + kwaliteitscatalogusopenbareruimte.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    kwaliteitscatalogusopenbareruimte.getId().toString()
                )
            )
            .body(kwaliteitscatalogusopenbareruimte);
    }

    /**
     * {@code GET  /kwaliteitscatalogusopenbareruimtes} : get all the kwaliteitscatalogusopenbareruimtes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kwaliteitscatalogusopenbareruimtes in body.
     */
    @GetMapping("")
    public List<Kwaliteitscatalogusopenbareruimte> getAllKwaliteitscatalogusopenbareruimtes() {
        log.debug("REST request to get all Kwaliteitscatalogusopenbareruimtes");
        return kwaliteitscatalogusopenbareruimteRepository.findAll();
    }

    /**
     * {@code GET  /kwaliteitscatalogusopenbareruimtes/:id} : get the "id" kwaliteitscatalogusopenbareruimte.
     *
     * @param id the id of the kwaliteitscatalogusopenbareruimte to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kwaliteitscatalogusopenbareruimte, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Kwaliteitscatalogusopenbareruimte> getKwaliteitscatalogusopenbareruimte(@PathVariable("id") Long id) {
        log.debug("REST request to get Kwaliteitscatalogusopenbareruimte : {}", id);
        Optional<Kwaliteitscatalogusopenbareruimte> kwaliteitscatalogusopenbareruimte =
            kwaliteitscatalogusopenbareruimteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(kwaliteitscatalogusopenbareruimte);
    }

    /**
     * {@code DELETE  /kwaliteitscatalogusopenbareruimtes/:id} : delete the "id" kwaliteitscatalogusopenbareruimte.
     *
     * @param id the id of the kwaliteitscatalogusopenbareruimte to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKwaliteitscatalogusopenbareruimte(@PathVariable("id") Long id) {
        log.debug("REST request to delete Kwaliteitscatalogusopenbareruimte : {}", id);
        kwaliteitscatalogusopenbareruimteRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
