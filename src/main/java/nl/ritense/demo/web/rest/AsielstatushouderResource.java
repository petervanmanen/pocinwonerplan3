package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Asielstatushouder;
import nl.ritense.demo.repository.AsielstatushouderRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Asielstatushouder}.
 */
@RestController
@RequestMapping("/api/asielstatushouders")
@Transactional
public class AsielstatushouderResource {

    private final Logger log = LoggerFactory.getLogger(AsielstatushouderResource.class);

    private static final String ENTITY_NAME = "asielstatushouder";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AsielstatushouderRepository asielstatushouderRepository;

    public AsielstatushouderResource(AsielstatushouderRepository asielstatushouderRepository) {
        this.asielstatushouderRepository = asielstatushouderRepository;
    }

    /**
     * {@code POST  /asielstatushouders} : Create a new asielstatushouder.
     *
     * @param asielstatushouder the asielstatushouder to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new asielstatushouder, or with status {@code 400 (Bad Request)} if the asielstatushouder has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Asielstatushouder> createAsielstatushouder(@Valid @RequestBody Asielstatushouder asielstatushouder)
        throws URISyntaxException {
        log.debug("REST request to save Asielstatushouder : {}", asielstatushouder);
        if (asielstatushouder.getId() != null) {
            throw new BadRequestAlertException("A new asielstatushouder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        asielstatushouder = asielstatushouderRepository.save(asielstatushouder);
        return ResponseEntity.created(new URI("/api/asielstatushouders/" + asielstatushouder.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, asielstatushouder.getId().toString()))
            .body(asielstatushouder);
    }

    /**
     * {@code PUT  /asielstatushouders/:id} : Updates an existing asielstatushouder.
     *
     * @param id the id of the asielstatushouder to save.
     * @param asielstatushouder the asielstatushouder to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated asielstatushouder,
     * or with status {@code 400 (Bad Request)} if the asielstatushouder is not valid,
     * or with status {@code 500 (Internal Server Error)} if the asielstatushouder couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Asielstatushouder> updateAsielstatushouder(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Asielstatushouder asielstatushouder
    ) throws URISyntaxException {
        log.debug("REST request to update Asielstatushouder : {}, {}", id, asielstatushouder);
        if (asielstatushouder.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, asielstatushouder.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!asielstatushouderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        asielstatushouder = asielstatushouderRepository.save(asielstatushouder);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, asielstatushouder.getId().toString()))
            .body(asielstatushouder);
    }

    /**
     * {@code PATCH  /asielstatushouders/:id} : Partial updates given fields of an existing asielstatushouder, field will ignore if it is null
     *
     * @param id the id of the asielstatushouder to save.
     * @param asielstatushouder the asielstatushouder to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated asielstatushouder,
     * or with status {@code 400 (Bad Request)} if the asielstatushouder is not valid,
     * or with status {@code 404 (Not Found)} if the asielstatushouder is not found,
     * or with status {@code 500 (Internal Server Error)} if the asielstatushouder couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Asielstatushouder> partialUpdateAsielstatushouder(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Asielstatushouder asielstatushouder
    ) throws URISyntaxException {
        log.debug("REST request to partial update Asielstatushouder partially : {}, {}", id, asielstatushouder);
        if (asielstatushouder.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, asielstatushouder.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!asielstatushouderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Asielstatushouder> result = asielstatushouderRepository
            .findById(asielstatushouder.getId())
            .map(existingAsielstatushouder -> {
                if (asielstatushouder.getDigidaangevraagd() != null) {
                    existingAsielstatushouder.setDigidaangevraagd(asielstatushouder.getDigidaangevraagd());
                }
                if (asielstatushouder.getEmailadresverblijfazc() != null) {
                    existingAsielstatushouder.setEmailadresverblijfazc(asielstatushouder.getEmailadresverblijfazc());
                }
                if (asielstatushouder.getIsgekoppeldaan() != null) {
                    existingAsielstatushouder.setIsgekoppeldaan(asielstatushouder.getIsgekoppeldaan());
                }
                if (asielstatushouder.getLandrijbewijs() != null) {
                    existingAsielstatushouder.setLandrijbewijs(asielstatushouder.getLandrijbewijs());
                }
                if (asielstatushouder.getRijbewijs() != null) {
                    existingAsielstatushouder.setRijbewijs(asielstatushouder.getRijbewijs());
                }
                if (asielstatushouder.getTelefoonnummerverblijfazc() != null) {
                    existingAsielstatushouder.setTelefoonnummerverblijfazc(asielstatushouder.getTelefoonnummerverblijfazc());
                }

                return existingAsielstatushouder;
            })
            .map(asielstatushouderRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, asielstatushouder.getId().toString())
        );
    }

    /**
     * {@code GET  /asielstatushouders} : get all the asielstatushouders.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of asielstatushouders in body.
     */
    @GetMapping("")
    public List<Asielstatushouder> getAllAsielstatushouders() {
        log.debug("REST request to get all Asielstatushouders");
        return asielstatushouderRepository.findAll();
    }

    /**
     * {@code GET  /asielstatushouders/:id} : get the "id" asielstatushouder.
     *
     * @param id the id of the asielstatushouder to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the asielstatushouder, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Asielstatushouder> getAsielstatushouder(@PathVariable("id") Long id) {
        log.debug("REST request to get Asielstatushouder : {}", id);
        Optional<Asielstatushouder> asielstatushouder = asielstatushouderRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(asielstatushouder);
    }

    /**
     * {@code DELETE  /asielstatushouders/:id} : delete the "id" asielstatushouder.
     *
     * @param id the id of the asielstatushouder to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAsielstatushouder(@PathVariable("id") Long id) {
        log.debug("REST request to delete Asielstatushouder : {}", id);
        asielstatushouderRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
