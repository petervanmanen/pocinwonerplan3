package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Juridischeregel;
import nl.ritense.demo.repository.JuridischeregelRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Juridischeregel}.
 */
@RestController
@RequestMapping("/api/juridischeregels")
@Transactional
public class JuridischeregelResource {

    private final Logger log = LoggerFactory.getLogger(JuridischeregelResource.class);

    private static final String ENTITY_NAME = "juridischeregel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JuridischeregelRepository juridischeregelRepository;

    public JuridischeregelResource(JuridischeregelRepository juridischeregelRepository) {
        this.juridischeregelRepository = juridischeregelRepository;
    }

    /**
     * {@code POST  /juridischeregels} : Create a new juridischeregel.
     *
     * @param juridischeregel the juridischeregel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new juridischeregel, or with status {@code 400 (Bad Request)} if the juridischeregel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Juridischeregel> createJuridischeregel(@RequestBody Juridischeregel juridischeregel) throws URISyntaxException {
        log.debug("REST request to save Juridischeregel : {}", juridischeregel);
        if (juridischeregel.getId() != null) {
            throw new BadRequestAlertException("A new juridischeregel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        juridischeregel = juridischeregelRepository.save(juridischeregel);
        return ResponseEntity.created(new URI("/api/juridischeregels/" + juridischeregel.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, juridischeregel.getId().toString()))
            .body(juridischeregel);
    }

    /**
     * {@code PUT  /juridischeregels/:id} : Updates an existing juridischeregel.
     *
     * @param id the id of the juridischeregel to save.
     * @param juridischeregel the juridischeregel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated juridischeregel,
     * or with status {@code 400 (Bad Request)} if the juridischeregel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the juridischeregel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Juridischeregel> updateJuridischeregel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Juridischeregel juridischeregel
    ) throws URISyntaxException {
        log.debug("REST request to update Juridischeregel : {}, {}", id, juridischeregel);
        if (juridischeregel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, juridischeregel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!juridischeregelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        juridischeregel = juridischeregelRepository.save(juridischeregel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, juridischeregel.getId().toString()))
            .body(juridischeregel);
    }

    /**
     * {@code PATCH  /juridischeregels/:id} : Partial updates given fields of an existing juridischeregel, field will ignore if it is null
     *
     * @param id the id of the juridischeregel to save.
     * @param juridischeregel the juridischeregel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated juridischeregel,
     * or with status {@code 400 (Bad Request)} if the juridischeregel is not valid,
     * or with status {@code 404 (Not Found)} if the juridischeregel is not found,
     * or with status {@code 500 (Internal Server Error)} if the juridischeregel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Juridischeregel> partialUpdateJuridischeregel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Juridischeregel juridischeregel
    ) throws URISyntaxException {
        log.debug("REST request to partial update Juridischeregel partially : {}, {}", id, juridischeregel);
        if (juridischeregel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, juridischeregel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!juridischeregelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Juridischeregel> result = juridischeregelRepository
            .findById(juridischeregel.getId())
            .map(existingJuridischeregel -> {
                if (juridischeregel.getDatumbekend() != null) {
                    existingJuridischeregel.setDatumbekend(juridischeregel.getDatumbekend());
                }
                if (juridischeregel.getDatumeindegeldigheid() != null) {
                    existingJuridischeregel.setDatumeindegeldigheid(juridischeregel.getDatumeindegeldigheid());
                }
                if (juridischeregel.getDatuminwerking() != null) {
                    existingJuridischeregel.setDatuminwerking(juridischeregel.getDatuminwerking());
                }
                if (juridischeregel.getDatumstart() != null) {
                    existingJuridischeregel.setDatumstart(juridischeregel.getDatumstart());
                }
                if (juridischeregel.getOmschrijving() != null) {
                    existingJuridischeregel.setOmschrijving(juridischeregel.getOmschrijving());
                }
                if (juridischeregel.getRegeltekst() != null) {
                    existingJuridischeregel.setRegeltekst(juridischeregel.getRegeltekst());
                }
                if (juridischeregel.getThema() != null) {
                    existingJuridischeregel.setThema(juridischeregel.getThema());
                }

                return existingJuridischeregel;
            })
            .map(juridischeregelRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, juridischeregel.getId().toString())
        );
    }

    /**
     * {@code GET  /juridischeregels} : get all the juridischeregels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of juridischeregels in body.
     */
    @GetMapping("")
    public List<Juridischeregel> getAllJuridischeregels() {
        log.debug("REST request to get all Juridischeregels");
        return juridischeregelRepository.findAll();
    }

    /**
     * {@code GET  /juridischeregels/:id} : get the "id" juridischeregel.
     *
     * @param id the id of the juridischeregel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the juridischeregel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Juridischeregel> getJuridischeregel(@PathVariable("id") Long id) {
        log.debug("REST request to get Juridischeregel : {}", id);
        Optional<Juridischeregel> juridischeregel = juridischeregelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(juridischeregel);
    }

    /**
     * {@code DELETE  /juridischeregels/:id} : delete the "id" juridischeregel.
     *
     * @param id the id of the juridischeregel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJuridischeregel(@PathVariable("id") Long id) {
        log.debug("REST request to delete Juridischeregel : {}", id);
        juridischeregelRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
