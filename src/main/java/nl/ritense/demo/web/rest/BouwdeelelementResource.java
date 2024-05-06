package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Bouwdeelelement;
import nl.ritense.demo.repository.BouwdeelelementRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Bouwdeelelement}.
 */
@RestController
@RequestMapping("/api/bouwdeelelements")
@Transactional
public class BouwdeelelementResource {

    private final Logger log = LoggerFactory.getLogger(BouwdeelelementResource.class);

    private static final String ENTITY_NAME = "bouwdeelelement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BouwdeelelementRepository bouwdeelelementRepository;

    public BouwdeelelementResource(BouwdeelelementRepository bouwdeelelementRepository) {
        this.bouwdeelelementRepository = bouwdeelelementRepository;
    }

    /**
     * {@code POST  /bouwdeelelements} : Create a new bouwdeelelement.
     *
     * @param bouwdeelelement the bouwdeelelement to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bouwdeelelement, or with status {@code 400 (Bad Request)} if the bouwdeelelement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Bouwdeelelement> createBouwdeelelement(@RequestBody Bouwdeelelement bouwdeelelement) throws URISyntaxException {
        log.debug("REST request to save Bouwdeelelement : {}", bouwdeelelement);
        if (bouwdeelelement.getId() != null) {
            throw new BadRequestAlertException("A new bouwdeelelement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        bouwdeelelement = bouwdeelelementRepository.save(bouwdeelelement);
        return ResponseEntity.created(new URI("/api/bouwdeelelements/" + bouwdeelelement.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, bouwdeelelement.getId().toString()))
            .body(bouwdeelelement);
    }

    /**
     * {@code PUT  /bouwdeelelements/:id} : Updates an existing bouwdeelelement.
     *
     * @param id the id of the bouwdeelelement to save.
     * @param bouwdeelelement the bouwdeelelement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bouwdeelelement,
     * or with status {@code 400 (Bad Request)} if the bouwdeelelement is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bouwdeelelement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Bouwdeelelement> updateBouwdeelelement(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Bouwdeelelement bouwdeelelement
    ) throws URISyntaxException {
        log.debug("REST request to update Bouwdeelelement : {}, {}", id, bouwdeelelement);
        if (bouwdeelelement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bouwdeelelement.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bouwdeelelementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        bouwdeelelement = bouwdeelelementRepository.save(bouwdeelelement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bouwdeelelement.getId().toString()))
            .body(bouwdeelelement);
    }

    /**
     * {@code PATCH  /bouwdeelelements/:id} : Partial updates given fields of an existing bouwdeelelement, field will ignore if it is null
     *
     * @param id the id of the bouwdeelelement to save.
     * @param bouwdeelelement the bouwdeelelement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bouwdeelelement,
     * or with status {@code 400 (Bad Request)} if the bouwdeelelement is not valid,
     * or with status {@code 404 (Not Found)} if the bouwdeelelement is not found,
     * or with status {@code 500 (Internal Server Error)} if the bouwdeelelement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Bouwdeelelement> partialUpdateBouwdeelelement(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Bouwdeelelement bouwdeelelement
    ) throws URISyntaxException {
        log.debug("REST request to partial update Bouwdeelelement partially : {}, {}", id, bouwdeelelement);
        if (bouwdeelelement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bouwdeelelement.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bouwdeelelementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Bouwdeelelement> result = bouwdeelelementRepository
            .findById(bouwdeelelement.getId())
            .map(existingBouwdeelelement -> {
                if (bouwdeelelement.getCode() != null) {
                    existingBouwdeelelement.setCode(bouwdeelelement.getCode());
                }
                if (bouwdeelelement.getOmschrijving() != null) {
                    existingBouwdeelelement.setOmschrijving(bouwdeelelement.getOmschrijving());
                }

                return existingBouwdeelelement;
            })
            .map(bouwdeelelementRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bouwdeelelement.getId().toString())
        );
    }

    /**
     * {@code GET  /bouwdeelelements} : get all the bouwdeelelements.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bouwdeelelements in body.
     */
    @GetMapping("")
    public List<Bouwdeelelement> getAllBouwdeelelements() {
        log.debug("REST request to get all Bouwdeelelements");
        return bouwdeelelementRepository.findAll();
    }

    /**
     * {@code GET  /bouwdeelelements/:id} : get the "id" bouwdeelelement.
     *
     * @param id the id of the bouwdeelelement to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bouwdeelelement, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Bouwdeelelement> getBouwdeelelement(@PathVariable("id") Long id) {
        log.debug("REST request to get Bouwdeelelement : {}", id);
        Optional<Bouwdeelelement> bouwdeelelement = bouwdeelelementRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bouwdeelelement);
    }

    /**
     * {@code DELETE  /bouwdeelelements/:id} : delete the "id" bouwdeelelement.
     *
     * @param id the id of the bouwdeelelement to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBouwdeelelement(@PathVariable("id") Long id) {
        log.debug("REST request to delete Bouwdeelelement : {}", id);
        bouwdeelelementRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
