package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Splitsingstekeningreferentie;
import nl.ritense.demo.repository.SplitsingstekeningreferentieRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Splitsingstekeningreferentie}.
 */
@RestController
@RequestMapping("/api/splitsingstekeningreferenties")
@Transactional
public class SplitsingstekeningreferentieResource {

    private final Logger log = LoggerFactory.getLogger(SplitsingstekeningreferentieResource.class);

    private static final String ENTITY_NAME = "splitsingstekeningreferentie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SplitsingstekeningreferentieRepository splitsingstekeningreferentieRepository;

    public SplitsingstekeningreferentieResource(SplitsingstekeningreferentieRepository splitsingstekeningreferentieRepository) {
        this.splitsingstekeningreferentieRepository = splitsingstekeningreferentieRepository;
    }

    /**
     * {@code POST  /splitsingstekeningreferenties} : Create a new splitsingstekeningreferentie.
     *
     * @param splitsingstekeningreferentie the splitsingstekeningreferentie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new splitsingstekeningreferentie, or with status {@code 400 (Bad Request)} if the splitsingstekeningreferentie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Splitsingstekeningreferentie> createSplitsingstekeningreferentie(
        @RequestBody Splitsingstekeningreferentie splitsingstekeningreferentie
    ) throws URISyntaxException {
        log.debug("REST request to save Splitsingstekeningreferentie : {}", splitsingstekeningreferentie);
        if (splitsingstekeningreferentie.getId() != null) {
            throw new BadRequestAlertException("A new splitsingstekeningreferentie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        splitsingstekeningreferentie = splitsingstekeningreferentieRepository.save(splitsingstekeningreferentie);
        return ResponseEntity.created(new URI("/api/splitsingstekeningreferenties/" + splitsingstekeningreferentie.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, splitsingstekeningreferentie.getId().toString())
            )
            .body(splitsingstekeningreferentie);
    }

    /**
     * {@code PUT  /splitsingstekeningreferenties/:id} : Updates an existing splitsingstekeningreferentie.
     *
     * @param id the id of the splitsingstekeningreferentie to save.
     * @param splitsingstekeningreferentie the splitsingstekeningreferentie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated splitsingstekeningreferentie,
     * or with status {@code 400 (Bad Request)} if the splitsingstekeningreferentie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the splitsingstekeningreferentie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Splitsingstekeningreferentie> updateSplitsingstekeningreferentie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Splitsingstekeningreferentie splitsingstekeningreferentie
    ) throws URISyntaxException {
        log.debug("REST request to update Splitsingstekeningreferentie : {}, {}", id, splitsingstekeningreferentie);
        if (splitsingstekeningreferentie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, splitsingstekeningreferentie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!splitsingstekeningreferentieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        splitsingstekeningreferentie = splitsingstekeningreferentieRepository.save(splitsingstekeningreferentie);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, splitsingstekeningreferentie.getId().toString())
            )
            .body(splitsingstekeningreferentie);
    }

    /**
     * {@code PATCH  /splitsingstekeningreferenties/:id} : Partial updates given fields of an existing splitsingstekeningreferentie, field will ignore if it is null
     *
     * @param id the id of the splitsingstekeningreferentie to save.
     * @param splitsingstekeningreferentie the splitsingstekeningreferentie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated splitsingstekeningreferentie,
     * or with status {@code 400 (Bad Request)} if the splitsingstekeningreferentie is not valid,
     * or with status {@code 404 (Not Found)} if the splitsingstekeningreferentie is not found,
     * or with status {@code 500 (Internal Server Error)} if the splitsingstekeningreferentie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Splitsingstekeningreferentie> partialUpdateSplitsingstekeningreferentie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Splitsingstekeningreferentie splitsingstekeningreferentie
    ) throws URISyntaxException {
        log.debug("REST request to partial update Splitsingstekeningreferentie partially : {}, {}", id, splitsingstekeningreferentie);
        if (splitsingstekeningreferentie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, splitsingstekeningreferentie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!splitsingstekeningreferentieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Splitsingstekeningreferentie> result = splitsingstekeningreferentieRepository
            .findById(splitsingstekeningreferentie.getId())
            .map(existingSplitsingstekeningreferentie -> {
                if (splitsingstekeningreferentie.getBronorganisatie() != null) {
                    existingSplitsingstekeningreferentie.setBronorganisatie(splitsingstekeningreferentie.getBronorganisatie());
                }
                if (splitsingstekeningreferentie.getDatumcreatie() != null) {
                    existingSplitsingstekeningreferentie.setDatumcreatie(splitsingstekeningreferentie.getDatumcreatie());
                }
                if (splitsingstekeningreferentie.getIdentificatietekening() != null) {
                    existingSplitsingstekeningreferentie.setIdentificatietekening(splitsingstekeningreferentie.getIdentificatietekening());
                }
                if (splitsingstekeningreferentie.getTitel() != null) {
                    existingSplitsingstekeningreferentie.setTitel(splitsingstekeningreferentie.getTitel());
                }

                return existingSplitsingstekeningreferentie;
            })
            .map(splitsingstekeningreferentieRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, splitsingstekeningreferentie.getId().toString())
        );
    }

    /**
     * {@code GET  /splitsingstekeningreferenties} : get all the splitsingstekeningreferenties.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of splitsingstekeningreferenties in body.
     */
    @GetMapping("")
    public List<Splitsingstekeningreferentie> getAllSplitsingstekeningreferenties() {
        log.debug("REST request to get all Splitsingstekeningreferenties");
        return splitsingstekeningreferentieRepository.findAll();
    }

    /**
     * {@code GET  /splitsingstekeningreferenties/:id} : get the "id" splitsingstekeningreferentie.
     *
     * @param id the id of the splitsingstekeningreferentie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the splitsingstekeningreferentie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Splitsingstekeningreferentie> getSplitsingstekeningreferentie(@PathVariable("id") Long id) {
        log.debug("REST request to get Splitsingstekeningreferentie : {}", id);
        Optional<Splitsingstekeningreferentie> splitsingstekeningreferentie = splitsingstekeningreferentieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(splitsingstekeningreferentie);
    }

    /**
     * {@code DELETE  /splitsingstekeningreferenties/:id} : delete the "id" splitsingstekeningreferentie.
     *
     * @param id the id of the splitsingstekeningreferentie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSplitsingstekeningreferentie(@PathVariable("id") Long id) {
        log.debug("REST request to delete Splitsingstekeningreferentie : {}", id);
        splitsingstekeningreferentieRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
