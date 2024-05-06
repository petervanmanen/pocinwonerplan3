package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Huurwoningen;
import nl.ritense.demo.repository.HuurwoningenRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Huurwoningen}.
 */
@RestController
@RequestMapping("/api/huurwoningens")
@Transactional
public class HuurwoningenResource {

    private final Logger log = LoggerFactory.getLogger(HuurwoningenResource.class);

    private static final String ENTITY_NAME = "huurwoningen";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HuurwoningenRepository huurwoningenRepository;

    public HuurwoningenResource(HuurwoningenRepository huurwoningenRepository) {
        this.huurwoningenRepository = huurwoningenRepository;
    }

    /**
     * {@code POST  /huurwoningens} : Create a new huurwoningen.
     *
     * @param huurwoningen the huurwoningen to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new huurwoningen, or with status {@code 400 (Bad Request)} if the huurwoningen has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Huurwoningen> createHuurwoningen(@RequestBody Huurwoningen huurwoningen) throws URISyntaxException {
        log.debug("REST request to save Huurwoningen : {}", huurwoningen);
        if (huurwoningen.getId() != null) {
            throw new BadRequestAlertException("A new huurwoningen cannot already have an ID", ENTITY_NAME, "idexists");
        }
        huurwoningen = huurwoningenRepository.save(huurwoningen);
        return ResponseEntity.created(new URI("/api/huurwoningens/" + huurwoningen.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, huurwoningen.getId().toString()))
            .body(huurwoningen);
    }

    /**
     * {@code PUT  /huurwoningens/:id} : Updates an existing huurwoningen.
     *
     * @param id the id of the huurwoningen to save.
     * @param huurwoningen the huurwoningen to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated huurwoningen,
     * or with status {@code 400 (Bad Request)} if the huurwoningen is not valid,
     * or with status {@code 500 (Internal Server Error)} if the huurwoningen couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Huurwoningen> updateHuurwoningen(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Huurwoningen huurwoningen
    ) throws URISyntaxException {
        log.debug("REST request to update Huurwoningen : {}, {}", id, huurwoningen);
        if (huurwoningen.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, huurwoningen.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!huurwoningenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        huurwoningen = huurwoningenRepository.save(huurwoningen);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, huurwoningen.getId().toString()))
            .body(huurwoningen);
    }

    /**
     * {@code PATCH  /huurwoningens/:id} : Partial updates given fields of an existing huurwoningen, field will ignore if it is null
     *
     * @param id the id of the huurwoningen to save.
     * @param huurwoningen the huurwoningen to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated huurwoningen,
     * or with status {@code 400 (Bad Request)} if the huurwoningen is not valid,
     * or with status {@code 404 (Not Found)} if the huurwoningen is not found,
     * or with status {@code 500 (Internal Server Error)} if the huurwoningen couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Huurwoningen> partialUpdateHuurwoningen(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Huurwoningen huurwoningen
    ) throws URISyntaxException {
        log.debug("REST request to partial update Huurwoningen partially : {}, {}", id, huurwoningen);
        if (huurwoningen.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, huurwoningen.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!huurwoningenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Huurwoningen> result = huurwoningenRepository
            .findById(huurwoningen.getId())
            .map(existingHuurwoningen -> {
                if (huurwoningen.getHuurprijs() != null) {
                    existingHuurwoningen.setHuurprijs(huurwoningen.getHuurprijs());
                }

                return existingHuurwoningen;
            })
            .map(huurwoningenRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, huurwoningen.getId().toString())
        );
    }

    /**
     * {@code GET  /huurwoningens} : get all the huurwoningens.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of huurwoningens in body.
     */
    @GetMapping("")
    public List<Huurwoningen> getAllHuurwoningens() {
        log.debug("REST request to get all Huurwoningens");
        return huurwoningenRepository.findAll();
    }

    /**
     * {@code GET  /huurwoningens/:id} : get the "id" huurwoningen.
     *
     * @param id the id of the huurwoningen to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the huurwoningen, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Huurwoningen> getHuurwoningen(@PathVariable("id") Long id) {
        log.debug("REST request to get Huurwoningen : {}", id);
        Optional<Huurwoningen> huurwoningen = huurwoningenRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(huurwoningen);
    }

    /**
     * {@code DELETE  /huurwoningens/:id} : delete the "id" huurwoningen.
     *
     * @param id the id of the huurwoningen to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHuurwoningen(@PathVariable("id") Long id) {
        log.debug("REST request to delete Huurwoningen : {}", id);
        huurwoningenRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
