package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Balieverkoopentreekaart;
import nl.ritense.demo.repository.BalieverkoopentreekaartRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Balieverkoopentreekaart}.
 */
@RestController
@RequestMapping("/api/balieverkoopentreekaarts")
@Transactional
public class BalieverkoopentreekaartResource {

    private final Logger log = LoggerFactory.getLogger(BalieverkoopentreekaartResource.class);

    private static final String ENTITY_NAME = "balieverkoopentreekaart";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BalieverkoopentreekaartRepository balieverkoopentreekaartRepository;

    public BalieverkoopentreekaartResource(BalieverkoopentreekaartRepository balieverkoopentreekaartRepository) {
        this.balieverkoopentreekaartRepository = balieverkoopentreekaartRepository;
    }

    /**
     * {@code POST  /balieverkoopentreekaarts} : Create a new balieverkoopentreekaart.
     *
     * @param balieverkoopentreekaart the balieverkoopentreekaart to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new balieverkoopentreekaart, or with status {@code 400 (Bad Request)} if the balieverkoopentreekaart has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Balieverkoopentreekaart> createBalieverkoopentreekaart(
        @RequestBody Balieverkoopentreekaart balieverkoopentreekaart
    ) throws URISyntaxException {
        log.debug("REST request to save Balieverkoopentreekaart : {}", balieverkoopentreekaart);
        if (balieverkoopentreekaart.getId() != null) {
            throw new BadRequestAlertException("A new balieverkoopentreekaart cannot already have an ID", ENTITY_NAME, "idexists");
        }
        balieverkoopentreekaart = balieverkoopentreekaartRepository.save(balieverkoopentreekaart);
        return ResponseEntity.created(new URI("/api/balieverkoopentreekaarts/" + balieverkoopentreekaart.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, balieverkoopentreekaart.getId().toString()))
            .body(balieverkoopentreekaart);
    }

    /**
     * {@code PUT  /balieverkoopentreekaarts/:id} : Updates an existing balieverkoopentreekaart.
     *
     * @param id the id of the balieverkoopentreekaart to save.
     * @param balieverkoopentreekaart the balieverkoopentreekaart to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated balieverkoopentreekaart,
     * or with status {@code 400 (Bad Request)} if the balieverkoopentreekaart is not valid,
     * or with status {@code 500 (Internal Server Error)} if the balieverkoopentreekaart couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Balieverkoopentreekaart> updateBalieverkoopentreekaart(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Balieverkoopentreekaart balieverkoopentreekaart
    ) throws URISyntaxException {
        log.debug("REST request to update Balieverkoopentreekaart : {}, {}", id, balieverkoopentreekaart);
        if (balieverkoopentreekaart.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, balieverkoopentreekaart.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!balieverkoopentreekaartRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        balieverkoopentreekaart = balieverkoopentreekaartRepository.save(balieverkoopentreekaart);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, balieverkoopentreekaart.getId().toString()))
            .body(balieverkoopentreekaart);
    }

    /**
     * {@code PATCH  /balieverkoopentreekaarts/:id} : Partial updates given fields of an existing balieverkoopentreekaart, field will ignore if it is null
     *
     * @param id the id of the balieverkoopentreekaart to save.
     * @param balieverkoopentreekaart the balieverkoopentreekaart to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated balieverkoopentreekaart,
     * or with status {@code 400 (Bad Request)} if the balieverkoopentreekaart is not valid,
     * or with status {@code 404 (Not Found)} if the balieverkoopentreekaart is not found,
     * or with status {@code 500 (Internal Server Error)} if the balieverkoopentreekaart couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Balieverkoopentreekaart> partialUpdateBalieverkoopentreekaart(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Balieverkoopentreekaart balieverkoopentreekaart
    ) throws URISyntaxException {
        log.debug("REST request to partial update Balieverkoopentreekaart partially : {}, {}", id, balieverkoopentreekaart);
        if (balieverkoopentreekaart.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, balieverkoopentreekaart.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!balieverkoopentreekaartRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Balieverkoopentreekaart> result = balieverkoopentreekaartRepository
            .findById(balieverkoopentreekaart.getId())
            .map(existingBalieverkoopentreekaart -> {
                if (balieverkoopentreekaart.getDatumeindegeldigheid() != null) {
                    existingBalieverkoopentreekaart.setDatumeindegeldigheid(balieverkoopentreekaart.getDatumeindegeldigheid());
                }
                if (balieverkoopentreekaart.getDatumstart() != null) {
                    existingBalieverkoopentreekaart.setDatumstart(balieverkoopentreekaart.getDatumstart());
                }
                if (balieverkoopentreekaart.getGebruiktop() != null) {
                    existingBalieverkoopentreekaart.setGebruiktop(balieverkoopentreekaart.getGebruiktop());
                }
                if (balieverkoopentreekaart.getRondleiding() != null) {
                    existingBalieverkoopentreekaart.setRondleiding(balieverkoopentreekaart.getRondleiding());
                }

                return existingBalieverkoopentreekaart;
            })
            .map(balieverkoopentreekaartRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, balieverkoopentreekaart.getId().toString())
        );
    }

    /**
     * {@code GET  /balieverkoopentreekaarts} : get all the balieverkoopentreekaarts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of balieverkoopentreekaarts in body.
     */
    @GetMapping("")
    public List<Balieverkoopentreekaart> getAllBalieverkoopentreekaarts() {
        log.debug("REST request to get all Balieverkoopentreekaarts");
        return balieverkoopentreekaartRepository.findAll();
    }

    /**
     * {@code GET  /balieverkoopentreekaarts/:id} : get the "id" balieverkoopentreekaart.
     *
     * @param id the id of the balieverkoopentreekaart to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the balieverkoopentreekaart, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Balieverkoopentreekaart> getBalieverkoopentreekaart(@PathVariable("id") Long id) {
        log.debug("REST request to get Balieverkoopentreekaart : {}", id);
        Optional<Balieverkoopentreekaart> balieverkoopentreekaart = balieverkoopentreekaartRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(balieverkoopentreekaart);
    }

    /**
     * {@code DELETE  /balieverkoopentreekaarts/:id} : delete the "id" balieverkoopentreekaart.
     *
     * @param id the id of the balieverkoopentreekaart to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBalieverkoopentreekaart(@PathVariable("id") Long id) {
        log.debug("REST request to delete Balieverkoopentreekaart : {}", id);
        balieverkoopentreekaartRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
