package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Wozbelang;
import nl.ritense.demo.repository.WozbelangRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Wozbelang}.
 */
@RestController
@RequestMapping("/api/wozbelangs")
@Transactional
public class WozbelangResource {

    private final Logger log = LoggerFactory.getLogger(WozbelangResource.class);

    private static final String ENTITY_NAME = "wozbelang";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WozbelangRepository wozbelangRepository;

    public WozbelangResource(WozbelangRepository wozbelangRepository) {
        this.wozbelangRepository = wozbelangRepository;
    }

    /**
     * {@code POST  /wozbelangs} : Create a new wozbelang.
     *
     * @param wozbelang the wozbelang to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new wozbelang, or with status {@code 400 (Bad Request)} if the wozbelang has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Wozbelang> createWozbelang(@RequestBody Wozbelang wozbelang) throws URISyntaxException {
        log.debug("REST request to save Wozbelang : {}", wozbelang);
        if (wozbelang.getId() != null) {
            throw new BadRequestAlertException("A new wozbelang cannot already have an ID", ENTITY_NAME, "idexists");
        }
        wozbelang = wozbelangRepository.save(wozbelang);
        return ResponseEntity.created(new URI("/api/wozbelangs/" + wozbelang.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, wozbelang.getId().toString()))
            .body(wozbelang);
    }

    /**
     * {@code PUT  /wozbelangs/:id} : Updates an existing wozbelang.
     *
     * @param id the id of the wozbelang to save.
     * @param wozbelang the wozbelang to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wozbelang,
     * or with status {@code 400 (Bad Request)} if the wozbelang is not valid,
     * or with status {@code 500 (Internal Server Error)} if the wozbelang couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Wozbelang> updateWozbelang(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Wozbelang wozbelang
    ) throws URISyntaxException {
        log.debug("REST request to update Wozbelang : {}, {}", id, wozbelang);
        if (wozbelang.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, wozbelang.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!wozbelangRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        wozbelang = wozbelangRepository.save(wozbelang);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, wozbelang.getId().toString()))
            .body(wozbelang);
    }

    /**
     * {@code PATCH  /wozbelangs/:id} : Partial updates given fields of an existing wozbelang, field will ignore if it is null
     *
     * @param id the id of the wozbelang to save.
     * @param wozbelang the wozbelang to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wozbelang,
     * or with status {@code 400 (Bad Request)} if the wozbelang is not valid,
     * or with status {@code 404 (Not Found)} if the wozbelang is not found,
     * or with status {@code 500 (Internal Server Error)} if the wozbelang couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Wozbelang> partialUpdateWozbelang(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Wozbelang wozbelang
    ) throws URISyntaxException {
        log.debug("REST request to partial update Wozbelang partially : {}, {}", id, wozbelang);
        if (wozbelang.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, wozbelang.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!wozbelangRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Wozbelang> result = wozbelangRepository
            .findById(wozbelang.getId())
            .map(existingWozbelang -> {
                if (wozbelang.getDatumbegingeldigheid() != null) {
                    existingWozbelang.setDatumbegingeldigheid(wozbelang.getDatumbegingeldigheid());
                }
                if (wozbelang.getDatumeindegeldigheid() != null) {
                    existingWozbelang.setDatumeindegeldigheid(wozbelang.getDatumeindegeldigheid());
                }
                if (wozbelang.getEigenaargebruiker() != null) {
                    existingWozbelang.setEigenaargebruiker(wozbelang.getEigenaargebruiker());
                }

                return existingWozbelang;
            })
            .map(wozbelangRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, wozbelang.getId().toString())
        );
    }

    /**
     * {@code GET  /wozbelangs} : get all the wozbelangs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of wozbelangs in body.
     */
    @GetMapping("")
    public List<Wozbelang> getAllWozbelangs() {
        log.debug("REST request to get all Wozbelangs");
        return wozbelangRepository.findAll();
    }

    /**
     * {@code GET  /wozbelangs/:id} : get the "id" wozbelang.
     *
     * @param id the id of the wozbelang to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the wozbelang, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Wozbelang> getWozbelang(@PathVariable("id") Long id) {
        log.debug("REST request to get Wozbelang : {}", id);
        Optional<Wozbelang> wozbelang = wozbelangRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(wozbelang);
    }

    /**
     * {@code DELETE  /wozbelangs/:id} : delete the "id" wozbelang.
     *
     * @param id the id of the wozbelang to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWozbelang(@PathVariable("id") Long id) {
        log.debug("REST request to delete Wozbelang : {}", id);
        wozbelangRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
