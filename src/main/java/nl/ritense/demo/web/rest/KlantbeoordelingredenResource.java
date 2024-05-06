package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Klantbeoordelingreden;
import nl.ritense.demo.repository.KlantbeoordelingredenRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Klantbeoordelingreden}.
 */
@RestController
@RequestMapping("/api/klantbeoordelingredens")
@Transactional
public class KlantbeoordelingredenResource {

    private final Logger log = LoggerFactory.getLogger(KlantbeoordelingredenResource.class);

    private static final String ENTITY_NAME = "klantbeoordelingreden";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KlantbeoordelingredenRepository klantbeoordelingredenRepository;

    public KlantbeoordelingredenResource(KlantbeoordelingredenRepository klantbeoordelingredenRepository) {
        this.klantbeoordelingredenRepository = klantbeoordelingredenRepository;
    }

    /**
     * {@code POST  /klantbeoordelingredens} : Create a new klantbeoordelingreden.
     *
     * @param klantbeoordelingreden the klantbeoordelingreden to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new klantbeoordelingreden, or with status {@code 400 (Bad Request)} if the klantbeoordelingreden has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Klantbeoordelingreden> createKlantbeoordelingreden(
        @Valid @RequestBody Klantbeoordelingreden klantbeoordelingreden
    ) throws URISyntaxException {
        log.debug("REST request to save Klantbeoordelingreden : {}", klantbeoordelingreden);
        if (klantbeoordelingreden.getId() != null) {
            throw new BadRequestAlertException("A new klantbeoordelingreden cannot already have an ID", ENTITY_NAME, "idexists");
        }
        klantbeoordelingreden = klantbeoordelingredenRepository.save(klantbeoordelingreden);
        return ResponseEntity.created(new URI("/api/klantbeoordelingredens/" + klantbeoordelingreden.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, klantbeoordelingreden.getId().toString()))
            .body(klantbeoordelingreden);
    }

    /**
     * {@code PUT  /klantbeoordelingredens/:id} : Updates an existing klantbeoordelingreden.
     *
     * @param id the id of the klantbeoordelingreden to save.
     * @param klantbeoordelingreden the klantbeoordelingreden to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated klantbeoordelingreden,
     * or with status {@code 400 (Bad Request)} if the klantbeoordelingreden is not valid,
     * or with status {@code 500 (Internal Server Error)} if the klantbeoordelingreden couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Klantbeoordelingreden> updateKlantbeoordelingreden(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Klantbeoordelingreden klantbeoordelingreden
    ) throws URISyntaxException {
        log.debug("REST request to update Klantbeoordelingreden : {}, {}", id, klantbeoordelingreden);
        if (klantbeoordelingreden.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, klantbeoordelingreden.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!klantbeoordelingredenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        klantbeoordelingreden = klantbeoordelingredenRepository.save(klantbeoordelingreden);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, klantbeoordelingreden.getId().toString()))
            .body(klantbeoordelingreden);
    }

    /**
     * {@code PATCH  /klantbeoordelingredens/:id} : Partial updates given fields of an existing klantbeoordelingreden, field will ignore if it is null
     *
     * @param id the id of the klantbeoordelingreden to save.
     * @param klantbeoordelingreden the klantbeoordelingreden to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated klantbeoordelingreden,
     * or with status {@code 400 (Bad Request)} if the klantbeoordelingreden is not valid,
     * or with status {@code 404 (Not Found)} if the klantbeoordelingreden is not found,
     * or with status {@code 500 (Internal Server Error)} if the klantbeoordelingreden couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Klantbeoordelingreden> partialUpdateKlantbeoordelingreden(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Klantbeoordelingreden klantbeoordelingreden
    ) throws URISyntaxException {
        log.debug("REST request to partial update Klantbeoordelingreden partially : {}, {}", id, klantbeoordelingreden);
        if (klantbeoordelingreden.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, klantbeoordelingreden.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!klantbeoordelingredenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Klantbeoordelingreden> result = klantbeoordelingredenRepository
            .findById(klantbeoordelingreden.getId())
            .map(existingKlantbeoordelingreden -> {
                if (klantbeoordelingreden.getReden() != null) {
                    existingKlantbeoordelingreden.setReden(klantbeoordelingreden.getReden());
                }

                return existingKlantbeoordelingreden;
            })
            .map(klantbeoordelingredenRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, klantbeoordelingreden.getId().toString())
        );
    }

    /**
     * {@code GET  /klantbeoordelingredens} : get all the klantbeoordelingredens.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of klantbeoordelingredens in body.
     */
    @GetMapping("")
    public List<Klantbeoordelingreden> getAllKlantbeoordelingredens() {
        log.debug("REST request to get all Klantbeoordelingredens");
        return klantbeoordelingredenRepository.findAll();
    }

    /**
     * {@code GET  /klantbeoordelingredens/:id} : get the "id" klantbeoordelingreden.
     *
     * @param id the id of the klantbeoordelingreden to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the klantbeoordelingreden, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Klantbeoordelingreden> getKlantbeoordelingreden(@PathVariable("id") Long id) {
        log.debug("REST request to get Klantbeoordelingreden : {}", id);
        Optional<Klantbeoordelingreden> klantbeoordelingreden = klantbeoordelingredenRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(klantbeoordelingreden);
    }

    /**
     * {@code DELETE  /klantbeoordelingredens/:id} : delete the "id" klantbeoordelingreden.
     *
     * @param id the id of the klantbeoordelingreden to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKlantbeoordelingreden(@PathVariable("id") Long id) {
        log.debug("REST request to delete Klantbeoordelingreden : {}", id);
        klantbeoordelingredenRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
