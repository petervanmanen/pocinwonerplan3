package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Samensteller;
import nl.ritense.demo.repository.SamenstellerRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Samensteller}.
 */
@RestController
@RequestMapping("/api/samenstellers")
@Transactional
public class SamenstellerResource {

    private final Logger log = LoggerFactory.getLogger(SamenstellerResource.class);

    private static final String ENTITY_NAME = "samensteller";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SamenstellerRepository samenstellerRepository;

    public SamenstellerResource(SamenstellerRepository samenstellerRepository) {
        this.samenstellerRepository = samenstellerRepository;
    }

    /**
     * {@code POST  /samenstellers} : Create a new samensteller.
     *
     * @param samensteller the samensteller to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new samensteller, or with status {@code 400 (Bad Request)} if the samensteller has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Samensteller> createSamensteller(@RequestBody Samensteller samensteller) throws URISyntaxException {
        log.debug("REST request to save Samensteller : {}", samensteller);
        if (samensteller.getId() != null) {
            throw new BadRequestAlertException("A new samensteller cannot already have an ID", ENTITY_NAME, "idexists");
        }
        samensteller = samenstellerRepository.save(samensteller);
        return ResponseEntity.created(new URI("/api/samenstellers/" + samensteller.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, samensteller.getId().toString()))
            .body(samensteller);
    }

    /**
     * {@code PUT  /samenstellers/:id} : Updates an existing samensteller.
     *
     * @param id the id of the samensteller to save.
     * @param samensteller the samensteller to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated samensteller,
     * or with status {@code 400 (Bad Request)} if the samensteller is not valid,
     * or with status {@code 500 (Internal Server Error)} if the samensteller couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Samensteller> updateSamensteller(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Samensteller samensteller
    ) throws URISyntaxException {
        log.debug("REST request to update Samensteller : {}, {}", id, samensteller);
        if (samensteller.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, samensteller.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!samenstellerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        samensteller = samenstellerRepository.save(samensteller);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, samensteller.getId().toString()))
            .body(samensteller);
    }

    /**
     * {@code PATCH  /samenstellers/:id} : Partial updates given fields of an existing samensteller, field will ignore if it is null
     *
     * @param id the id of the samensteller to save.
     * @param samensteller the samensteller to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated samensteller,
     * or with status {@code 400 (Bad Request)} if the samensteller is not valid,
     * or with status {@code 404 (Not Found)} if the samensteller is not found,
     * or with status {@code 500 (Internal Server Error)} if the samensteller couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Samensteller> partialUpdateSamensteller(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Samensteller samensteller
    ) throws URISyntaxException {
        log.debug("REST request to partial update Samensteller partially : {}, {}", id, samensteller);
        if (samensteller.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, samensteller.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!samenstellerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Samensteller> result = samenstellerRepository
            .findById(samensteller.getId())
            .map(existingSamensteller -> {
                if (samensteller.getRol() != null) {
                    existingSamensteller.setRol(samensteller.getRol());
                }

                return existingSamensteller;
            })
            .map(samenstellerRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, samensteller.getId().toString())
        );
    }

    /**
     * {@code GET  /samenstellers} : get all the samenstellers.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of samenstellers in body.
     */
    @GetMapping("")
    public List<Samensteller> getAllSamenstellers(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all Samenstellers");
        if (eagerload) {
            return samenstellerRepository.findAllWithEagerRelationships();
        } else {
            return samenstellerRepository.findAll();
        }
    }

    /**
     * {@code GET  /samenstellers/:id} : get the "id" samensteller.
     *
     * @param id the id of the samensteller to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the samensteller, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Samensteller> getSamensteller(@PathVariable("id") Long id) {
        log.debug("REST request to get Samensteller : {}", id);
        Optional<Samensteller> samensteller = samenstellerRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(samensteller);
    }

    /**
     * {@code DELETE  /samenstellers/:id} : delete the "id" samensteller.
     *
     * @param id the id of the samensteller to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSamensteller(@PathVariable("id") Long id) {
        log.debug("REST request to delete Samensteller : {}", id);
        samenstellerRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
