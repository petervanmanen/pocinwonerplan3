package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Toepasbareregel;
import nl.ritense.demo.repository.ToepasbareregelRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Toepasbareregel}.
 */
@RestController
@RequestMapping("/api/toepasbareregels")
@Transactional
public class ToepasbareregelResource {

    private final Logger log = LoggerFactory.getLogger(ToepasbareregelResource.class);

    private static final String ENTITY_NAME = "toepasbareregel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ToepasbareregelRepository toepasbareregelRepository;

    public ToepasbareregelResource(ToepasbareregelRepository toepasbareregelRepository) {
        this.toepasbareregelRepository = toepasbareregelRepository;
    }

    /**
     * {@code POST  /toepasbareregels} : Create a new toepasbareregel.
     *
     * @param toepasbareregel the toepasbareregel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new toepasbareregel, or with status {@code 400 (Bad Request)} if the toepasbareregel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Toepasbareregel> createToepasbareregel(@RequestBody Toepasbareregel toepasbareregel) throws URISyntaxException {
        log.debug("REST request to save Toepasbareregel : {}", toepasbareregel);
        if (toepasbareregel.getId() != null) {
            throw new BadRequestAlertException("A new toepasbareregel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        toepasbareregel = toepasbareregelRepository.save(toepasbareregel);
        return ResponseEntity.created(new URI("/api/toepasbareregels/" + toepasbareregel.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, toepasbareregel.getId().toString()))
            .body(toepasbareregel);
    }

    /**
     * {@code PUT  /toepasbareregels/:id} : Updates an existing toepasbareregel.
     *
     * @param id the id of the toepasbareregel to save.
     * @param toepasbareregel the toepasbareregel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated toepasbareregel,
     * or with status {@code 400 (Bad Request)} if the toepasbareregel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the toepasbareregel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Toepasbareregel> updateToepasbareregel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Toepasbareregel toepasbareregel
    ) throws URISyntaxException {
        log.debug("REST request to update Toepasbareregel : {}, {}", id, toepasbareregel);
        if (toepasbareregel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, toepasbareregel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!toepasbareregelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        toepasbareregel = toepasbareregelRepository.save(toepasbareregel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, toepasbareregel.getId().toString()))
            .body(toepasbareregel);
    }

    /**
     * {@code PATCH  /toepasbareregels/:id} : Partial updates given fields of an existing toepasbareregel, field will ignore if it is null
     *
     * @param id the id of the toepasbareregel to save.
     * @param toepasbareregel the toepasbareregel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated toepasbareregel,
     * or with status {@code 400 (Bad Request)} if the toepasbareregel is not valid,
     * or with status {@code 404 (Not Found)} if the toepasbareregel is not found,
     * or with status {@code 500 (Internal Server Error)} if the toepasbareregel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Toepasbareregel> partialUpdateToepasbareregel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Toepasbareregel toepasbareregel
    ) throws URISyntaxException {
        log.debug("REST request to partial update Toepasbareregel partially : {}, {}", id, toepasbareregel);
        if (toepasbareregel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, toepasbareregel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!toepasbareregelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Toepasbareregel> result = toepasbareregelRepository
            .findById(toepasbareregel.getId())
            .map(existingToepasbareregel -> {
                if (toepasbareregel.getDatumbegingeldigheid() != null) {
                    existingToepasbareregel.setDatumbegingeldigheid(toepasbareregel.getDatumbegingeldigheid());
                }
                if (toepasbareregel.getDatumeindegeldigheid() != null) {
                    existingToepasbareregel.setDatumeindegeldigheid(toepasbareregel.getDatumeindegeldigheid());
                }
                if (toepasbareregel.getDomein() != null) {
                    existingToepasbareregel.setDomein(toepasbareregel.getDomein());
                }
                if (toepasbareregel.getNaam() != null) {
                    existingToepasbareregel.setNaam(toepasbareregel.getNaam());
                }
                if (toepasbareregel.getOmschrijving() != null) {
                    existingToepasbareregel.setOmschrijving(toepasbareregel.getOmschrijving());
                }
                if (toepasbareregel.getSoortaansluitpunt() != null) {
                    existingToepasbareregel.setSoortaansluitpunt(toepasbareregel.getSoortaansluitpunt());
                }
                if (toepasbareregel.getToestemming() != null) {
                    existingToepasbareregel.setToestemming(toepasbareregel.getToestemming());
                }

                return existingToepasbareregel;
            })
            .map(toepasbareregelRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, toepasbareregel.getId().toString())
        );
    }

    /**
     * {@code GET  /toepasbareregels} : get all the toepasbareregels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of toepasbareregels in body.
     */
    @GetMapping("")
    public List<Toepasbareregel> getAllToepasbareregels() {
        log.debug("REST request to get all Toepasbareregels");
        return toepasbareregelRepository.findAll();
    }

    /**
     * {@code GET  /toepasbareregels/:id} : get the "id" toepasbareregel.
     *
     * @param id the id of the toepasbareregel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the toepasbareregel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Toepasbareregel> getToepasbareregel(@PathVariable("id") Long id) {
        log.debug("REST request to get Toepasbareregel : {}", id);
        Optional<Toepasbareregel> toepasbareregel = toepasbareregelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(toepasbareregel);
    }

    /**
     * {@code DELETE  /toepasbareregels/:id} : delete the "id" toepasbareregel.
     *
     * @param id the id of the toepasbareregel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteToepasbareregel(@PathVariable("id") Long id) {
        log.debug("REST request to delete Toepasbareregel : {}", id);
        toepasbareregelRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
