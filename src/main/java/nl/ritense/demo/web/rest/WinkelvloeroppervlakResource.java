package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Winkelvloeroppervlak;
import nl.ritense.demo.repository.WinkelvloeroppervlakRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Winkelvloeroppervlak}.
 */
@RestController
@RequestMapping("/api/winkelvloeroppervlaks")
@Transactional
public class WinkelvloeroppervlakResource {

    private final Logger log = LoggerFactory.getLogger(WinkelvloeroppervlakResource.class);

    private static final String ENTITY_NAME = "winkelvloeroppervlak";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WinkelvloeroppervlakRepository winkelvloeroppervlakRepository;

    public WinkelvloeroppervlakResource(WinkelvloeroppervlakRepository winkelvloeroppervlakRepository) {
        this.winkelvloeroppervlakRepository = winkelvloeroppervlakRepository;
    }

    /**
     * {@code POST  /winkelvloeroppervlaks} : Create a new winkelvloeroppervlak.
     *
     * @param winkelvloeroppervlak the winkelvloeroppervlak to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new winkelvloeroppervlak, or with status {@code 400 (Bad Request)} if the winkelvloeroppervlak has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Winkelvloeroppervlak> createWinkelvloeroppervlak(@RequestBody Winkelvloeroppervlak winkelvloeroppervlak)
        throws URISyntaxException {
        log.debug("REST request to save Winkelvloeroppervlak : {}", winkelvloeroppervlak);
        if (winkelvloeroppervlak.getId() != null) {
            throw new BadRequestAlertException("A new winkelvloeroppervlak cannot already have an ID", ENTITY_NAME, "idexists");
        }
        winkelvloeroppervlak = winkelvloeroppervlakRepository.save(winkelvloeroppervlak);
        return ResponseEntity.created(new URI("/api/winkelvloeroppervlaks/" + winkelvloeroppervlak.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, winkelvloeroppervlak.getId().toString()))
            .body(winkelvloeroppervlak);
    }

    /**
     * {@code PUT  /winkelvloeroppervlaks/:id} : Updates an existing winkelvloeroppervlak.
     *
     * @param id the id of the winkelvloeroppervlak to save.
     * @param winkelvloeroppervlak the winkelvloeroppervlak to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated winkelvloeroppervlak,
     * or with status {@code 400 (Bad Request)} if the winkelvloeroppervlak is not valid,
     * or with status {@code 500 (Internal Server Error)} if the winkelvloeroppervlak couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Winkelvloeroppervlak> updateWinkelvloeroppervlak(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Winkelvloeroppervlak winkelvloeroppervlak
    ) throws URISyntaxException {
        log.debug("REST request to update Winkelvloeroppervlak : {}, {}", id, winkelvloeroppervlak);
        if (winkelvloeroppervlak.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, winkelvloeroppervlak.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!winkelvloeroppervlakRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        winkelvloeroppervlak = winkelvloeroppervlakRepository.save(winkelvloeroppervlak);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, winkelvloeroppervlak.getId().toString()))
            .body(winkelvloeroppervlak);
    }

    /**
     * {@code PATCH  /winkelvloeroppervlaks/:id} : Partial updates given fields of an existing winkelvloeroppervlak, field will ignore if it is null
     *
     * @param id the id of the winkelvloeroppervlak to save.
     * @param winkelvloeroppervlak the winkelvloeroppervlak to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated winkelvloeroppervlak,
     * or with status {@code 400 (Bad Request)} if the winkelvloeroppervlak is not valid,
     * or with status {@code 404 (Not Found)} if the winkelvloeroppervlak is not found,
     * or with status {@code 500 (Internal Server Error)} if the winkelvloeroppervlak couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Winkelvloeroppervlak> partialUpdateWinkelvloeroppervlak(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Winkelvloeroppervlak winkelvloeroppervlak
    ) throws URISyntaxException {
        log.debug("REST request to partial update Winkelvloeroppervlak partially : {}, {}", id, winkelvloeroppervlak);
        if (winkelvloeroppervlak.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, winkelvloeroppervlak.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!winkelvloeroppervlakRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Winkelvloeroppervlak> result = winkelvloeroppervlakRepository
            .findById(winkelvloeroppervlak.getId())
            .map(existingWinkelvloeroppervlak -> {
                if (winkelvloeroppervlak.getAantalkassa() != null) {
                    existingWinkelvloeroppervlak.setAantalkassa(winkelvloeroppervlak.getAantalkassa());
                }
                if (winkelvloeroppervlak.getBronwvo() != null) {
                    existingWinkelvloeroppervlak.setBronwvo(winkelvloeroppervlak.getBronwvo());
                }
                if (winkelvloeroppervlak.getLeegstand() != null) {
                    existingWinkelvloeroppervlak.setLeegstand(winkelvloeroppervlak.getLeegstand());
                }
                if (winkelvloeroppervlak.getWinkelvloeroppervlakte() != null) {
                    existingWinkelvloeroppervlak.setWinkelvloeroppervlakte(winkelvloeroppervlak.getWinkelvloeroppervlakte());
                }
                if (winkelvloeroppervlak.getWvoklasse() != null) {
                    existingWinkelvloeroppervlak.setWvoklasse(winkelvloeroppervlak.getWvoklasse());
                }

                return existingWinkelvloeroppervlak;
            })
            .map(winkelvloeroppervlakRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, winkelvloeroppervlak.getId().toString())
        );
    }

    /**
     * {@code GET  /winkelvloeroppervlaks} : get all the winkelvloeroppervlaks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of winkelvloeroppervlaks in body.
     */
    @GetMapping("")
    public List<Winkelvloeroppervlak> getAllWinkelvloeroppervlaks() {
        log.debug("REST request to get all Winkelvloeroppervlaks");
        return winkelvloeroppervlakRepository.findAll();
    }

    /**
     * {@code GET  /winkelvloeroppervlaks/:id} : get the "id" winkelvloeroppervlak.
     *
     * @param id the id of the winkelvloeroppervlak to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the winkelvloeroppervlak, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Winkelvloeroppervlak> getWinkelvloeroppervlak(@PathVariable("id") Long id) {
        log.debug("REST request to get Winkelvloeroppervlak : {}", id);
        Optional<Winkelvloeroppervlak> winkelvloeroppervlak = winkelvloeroppervlakRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(winkelvloeroppervlak);
    }

    /**
     * {@code DELETE  /winkelvloeroppervlaks/:id} : delete the "id" winkelvloeroppervlak.
     *
     * @param id the id of the winkelvloeroppervlak to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWinkelvloeroppervlak(@PathVariable("id") Long id) {
        log.debug("REST request to delete Winkelvloeroppervlak : {}", id);
        winkelvloeroppervlakRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
