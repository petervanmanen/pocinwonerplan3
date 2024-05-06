package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Aardaantekening;
import nl.ritense.demo.repository.AardaantekeningRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Aardaantekening}.
 */
@RestController
@RequestMapping("/api/aardaantekenings")
@Transactional
public class AardaantekeningResource {

    private final Logger log = LoggerFactory.getLogger(AardaantekeningResource.class);

    private static final String ENTITY_NAME = "aardaantekening";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AardaantekeningRepository aardaantekeningRepository;

    public AardaantekeningResource(AardaantekeningRepository aardaantekeningRepository) {
        this.aardaantekeningRepository = aardaantekeningRepository;
    }

    /**
     * {@code POST  /aardaantekenings} : Create a new aardaantekening.
     *
     * @param aardaantekening the aardaantekening to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aardaantekening, or with status {@code 400 (Bad Request)} if the aardaantekening has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Aardaantekening> createAardaantekening(@RequestBody Aardaantekening aardaantekening) throws URISyntaxException {
        log.debug("REST request to save Aardaantekening : {}", aardaantekening);
        if (aardaantekening.getId() != null) {
            throw new BadRequestAlertException("A new aardaantekening cannot already have an ID", ENTITY_NAME, "idexists");
        }
        aardaantekening = aardaantekeningRepository.save(aardaantekening);
        return ResponseEntity.created(new URI("/api/aardaantekenings/" + aardaantekening.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, aardaantekening.getId().toString()))
            .body(aardaantekening);
    }

    /**
     * {@code PUT  /aardaantekenings/:id} : Updates an existing aardaantekening.
     *
     * @param id the id of the aardaantekening to save.
     * @param aardaantekening the aardaantekening to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aardaantekening,
     * or with status {@code 400 (Bad Request)} if the aardaantekening is not valid,
     * or with status {@code 500 (Internal Server Error)} if the aardaantekening couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Aardaantekening> updateAardaantekening(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Aardaantekening aardaantekening
    ) throws URISyntaxException {
        log.debug("REST request to update Aardaantekening : {}, {}", id, aardaantekening);
        if (aardaantekening.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aardaantekening.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aardaantekeningRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        aardaantekening = aardaantekeningRepository.save(aardaantekening);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, aardaantekening.getId().toString()))
            .body(aardaantekening);
    }

    /**
     * {@code PATCH  /aardaantekenings/:id} : Partial updates given fields of an existing aardaantekening, field will ignore if it is null
     *
     * @param id the id of the aardaantekening to save.
     * @param aardaantekening the aardaantekening to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aardaantekening,
     * or with status {@code 400 (Bad Request)} if the aardaantekening is not valid,
     * or with status {@code 404 (Not Found)} if the aardaantekening is not found,
     * or with status {@code 500 (Internal Server Error)} if the aardaantekening couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Aardaantekening> partialUpdateAardaantekening(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Aardaantekening aardaantekening
    ) throws URISyntaxException {
        log.debug("REST request to partial update Aardaantekening partially : {}, {}", id, aardaantekening);
        if (aardaantekening.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aardaantekening.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aardaantekeningRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Aardaantekening> result = aardaantekeningRepository
            .findById(aardaantekening.getId())
            .map(existingAardaantekening -> {
                if (aardaantekening.getCodeaardaantekening() != null) {
                    existingAardaantekening.setCodeaardaantekening(aardaantekening.getCodeaardaantekening());
                }
                if (aardaantekening.getDatumbegingeldigheidaardaantekening() != null) {
                    existingAardaantekening.setDatumbegingeldigheidaardaantekening(
                        aardaantekening.getDatumbegingeldigheidaardaantekening()
                    );
                }
                if (aardaantekening.getDatumeindegeldigheidaardaantekening() != null) {
                    existingAardaantekening.setDatumeindegeldigheidaardaantekening(
                        aardaantekening.getDatumeindegeldigheidaardaantekening()
                    );
                }
                if (aardaantekening.getNaamaardaantekening() != null) {
                    existingAardaantekening.setNaamaardaantekening(aardaantekening.getNaamaardaantekening());
                }

                return existingAardaantekening;
            })
            .map(aardaantekeningRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, aardaantekening.getId().toString())
        );
    }

    /**
     * {@code GET  /aardaantekenings} : get all the aardaantekenings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aardaantekenings in body.
     */
    @GetMapping("")
    public List<Aardaantekening> getAllAardaantekenings() {
        log.debug("REST request to get all Aardaantekenings");
        return aardaantekeningRepository.findAll();
    }

    /**
     * {@code GET  /aardaantekenings/:id} : get the "id" aardaantekening.
     *
     * @param id the id of the aardaantekening to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aardaantekening, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Aardaantekening> getAardaantekening(@PathVariable("id") Long id) {
        log.debug("REST request to get Aardaantekening : {}", id);
        Optional<Aardaantekening> aardaantekening = aardaantekeningRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(aardaantekening);
    }

    /**
     * {@code DELETE  /aardaantekenings/:id} : delete the "id" aardaantekening.
     *
     * @param id the id of the aardaantekening to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAardaantekening(@PathVariable("id") Long id) {
        log.debug("REST request to delete Aardaantekening : {}", id);
        aardaantekeningRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
