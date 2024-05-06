package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Wijk;
import nl.ritense.demo.repository.WijkRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Wijk}.
 */
@RestController
@RequestMapping("/api/wijks")
@Transactional
public class WijkResource {

    private final Logger log = LoggerFactory.getLogger(WijkResource.class);

    private static final String ENTITY_NAME = "wijk";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WijkRepository wijkRepository;

    public WijkResource(WijkRepository wijkRepository) {
        this.wijkRepository = wijkRepository;
    }

    /**
     * {@code POST  /wijks} : Create a new wijk.
     *
     * @param wijk the wijk to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new wijk, or with status {@code 400 (Bad Request)} if the wijk has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Wijk> createWijk(@Valid @RequestBody Wijk wijk) throws URISyntaxException {
        log.debug("REST request to save Wijk : {}", wijk);
        if (wijk.getId() != null) {
            throw new BadRequestAlertException("A new wijk cannot already have an ID", ENTITY_NAME, "idexists");
        }
        wijk = wijkRepository.save(wijk);
        return ResponseEntity.created(new URI("/api/wijks/" + wijk.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, wijk.getId().toString()))
            .body(wijk);
    }

    /**
     * {@code PUT  /wijks/:id} : Updates an existing wijk.
     *
     * @param id the id of the wijk to save.
     * @param wijk the wijk to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wijk,
     * or with status {@code 400 (Bad Request)} if the wijk is not valid,
     * or with status {@code 500 (Internal Server Error)} if the wijk couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Wijk> updateWijk(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Wijk wijk)
        throws URISyntaxException {
        log.debug("REST request to update Wijk : {}, {}", id, wijk);
        if (wijk.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, wijk.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!wijkRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        wijk = wijkRepository.save(wijk);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, wijk.getId().toString()))
            .body(wijk);
    }

    /**
     * {@code PATCH  /wijks/:id} : Partial updates given fields of an existing wijk, field will ignore if it is null
     *
     * @param id the id of the wijk to save.
     * @param wijk the wijk to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wijk,
     * or with status {@code 400 (Bad Request)} if the wijk is not valid,
     * or with status {@code 404 (Not Found)} if the wijk is not found,
     * or with status {@code 500 (Internal Server Error)} if the wijk couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Wijk> partialUpdateWijk(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Wijk wijk
    ) throws URISyntaxException {
        log.debug("REST request to partial update Wijk partially : {}, {}", id, wijk);
        if (wijk.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, wijk.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!wijkRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Wijk> result = wijkRepository
            .findById(wijk.getId())
            .map(existingWijk -> {
                if (wijk.getDatumbegingeldigheid() != null) {
                    existingWijk.setDatumbegingeldigheid(wijk.getDatumbegingeldigheid());
                }
                if (wijk.getDatumeinde() != null) {
                    existingWijk.setDatumeinde(wijk.getDatumeinde());
                }
                if (wijk.getDatumeindegeldigheid() != null) {
                    existingWijk.setDatumeindegeldigheid(wijk.getDatumeindegeldigheid());
                }
                if (wijk.getDatumingang() != null) {
                    existingWijk.setDatumingang(wijk.getDatumingang());
                }
                if (wijk.getGeconstateerd() != null) {
                    existingWijk.setGeconstateerd(wijk.getGeconstateerd());
                }
                if (wijk.getGeometrie() != null) {
                    existingWijk.setGeometrie(wijk.getGeometrie());
                }
                if (wijk.getIdentificatie() != null) {
                    existingWijk.setIdentificatie(wijk.getIdentificatie());
                }
                if (wijk.getInonderzoek() != null) {
                    existingWijk.setInonderzoek(wijk.getInonderzoek());
                }
                if (wijk.getStatus() != null) {
                    existingWijk.setStatus(wijk.getStatus());
                }
                if (wijk.getVersie() != null) {
                    existingWijk.setVersie(wijk.getVersie());
                }
                if (wijk.getWijkcode() != null) {
                    existingWijk.setWijkcode(wijk.getWijkcode());
                }
                if (wijk.getWijknaam() != null) {
                    existingWijk.setWijknaam(wijk.getWijknaam());
                }

                return existingWijk;
            })
            .map(wijkRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, wijk.getId().toString())
        );
    }

    /**
     * {@code GET  /wijks} : get all the wijks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of wijks in body.
     */
    @GetMapping("")
    public List<Wijk> getAllWijks() {
        log.debug("REST request to get all Wijks");
        return wijkRepository.findAll();
    }

    /**
     * {@code GET  /wijks/:id} : get the "id" wijk.
     *
     * @param id the id of the wijk to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the wijk, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Wijk> getWijk(@PathVariable("id") Long id) {
        log.debug("REST request to get Wijk : {}", id);
        Optional<Wijk> wijk = wijkRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(wijk);
    }

    /**
     * {@code DELETE  /wijks/:id} : delete the "id" wijk.
     *
     * @param id the id of the wijk to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWijk(@PathVariable("id") Long id) {
        log.debug("REST request to delete Wijk : {}", id);
        wijkRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
