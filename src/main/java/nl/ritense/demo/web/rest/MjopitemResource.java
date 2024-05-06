package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Mjopitem;
import nl.ritense.demo.repository.MjopitemRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Mjopitem}.
 */
@RestController
@RequestMapping("/api/mjopitems")
@Transactional
public class MjopitemResource {

    private final Logger log = LoggerFactory.getLogger(MjopitemResource.class);

    private static final String ENTITY_NAME = "mjopitem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MjopitemRepository mjopitemRepository;

    public MjopitemResource(MjopitemRepository mjopitemRepository) {
        this.mjopitemRepository = mjopitemRepository;
    }

    /**
     * {@code POST  /mjopitems} : Create a new mjopitem.
     *
     * @param mjopitem the mjopitem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mjopitem, or with status {@code 400 (Bad Request)} if the mjopitem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Mjopitem> createMjopitem(@RequestBody Mjopitem mjopitem) throws URISyntaxException {
        log.debug("REST request to save Mjopitem : {}", mjopitem);
        if (mjopitem.getId() != null) {
            throw new BadRequestAlertException("A new mjopitem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        mjopitem = mjopitemRepository.save(mjopitem);
        return ResponseEntity.created(new URI("/api/mjopitems/" + mjopitem.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, mjopitem.getId().toString()))
            .body(mjopitem);
    }

    /**
     * {@code PUT  /mjopitems/:id} : Updates an existing mjopitem.
     *
     * @param id the id of the mjopitem to save.
     * @param mjopitem the mjopitem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mjopitem,
     * or with status {@code 400 (Bad Request)} if the mjopitem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mjopitem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Mjopitem> updateMjopitem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Mjopitem mjopitem
    ) throws URISyntaxException {
        log.debug("REST request to update Mjopitem : {}, {}", id, mjopitem);
        if (mjopitem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mjopitem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mjopitemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        mjopitem = mjopitemRepository.save(mjopitem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, mjopitem.getId().toString()))
            .body(mjopitem);
    }

    /**
     * {@code PATCH  /mjopitems/:id} : Partial updates given fields of an existing mjopitem, field will ignore if it is null
     *
     * @param id the id of the mjopitem to save.
     * @param mjopitem the mjopitem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mjopitem,
     * or with status {@code 400 (Bad Request)} if the mjopitem is not valid,
     * or with status {@code 404 (Not Found)} if the mjopitem is not found,
     * or with status {@code 500 (Internal Server Error)} if the mjopitem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Mjopitem> partialUpdateMjopitem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Mjopitem mjopitem
    ) throws URISyntaxException {
        log.debug("REST request to partial update Mjopitem partially : {}, {}", id, mjopitem);
        if (mjopitem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mjopitem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mjopitemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Mjopitem> result = mjopitemRepository
            .findById(mjopitem.getId())
            .map(existingMjopitem -> {
                if (mjopitem.getCode() != null) {
                    existingMjopitem.setCode(mjopitem.getCode());
                }
                if (mjopitem.getDatumeinde() != null) {
                    existingMjopitem.setDatumeinde(mjopitem.getDatumeinde());
                }
                if (mjopitem.getDatumopzeggingaanbieder() != null) {
                    existingMjopitem.setDatumopzeggingaanbieder(mjopitem.getDatumopzeggingaanbieder());
                }
                if (mjopitem.getDatumopzeggingontvanger() != null) {
                    existingMjopitem.setDatumopzeggingontvanger(mjopitem.getDatumopzeggingontvanger());
                }
                if (mjopitem.getDatumstart() != null) {
                    existingMjopitem.setDatumstart(mjopitem.getDatumstart());
                }
                if (mjopitem.getKosten() != null) {
                    existingMjopitem.setKosten(mjopitem.getKosten());
                }
                if (mjopitem.getOmschrijving() != null) {
                    existingMjopitem.setOmschrijving(mjopitem.getOmschrijving());
                }
                if (mjopitem.getOpzegtermijnaanbieder() != null) {
                    existingMjopitem.setOpzegtermijnaanbieder(mjopitem.getOpzegtermijnaanbieder());
                }
                if (mjopitem.getOpzegtermijnontvanger() != null) {
                    existingMjopitem.setOpzegtermijnontvanger(mjopitem.getOpzegtermijnontvanger());
                }

                return existingMjopitem;
            })
            .map(mjopitemRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, mjopitem.getId().toString())
        );
    }

    /**
     * {@code GET  /mjopitems} : get all the mjopitems.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mjopitems in body.
     */
    @GetMapping("")
    public List<Mjopitem> getAllMjopitems() {
        log.debug("REST request to get all Mjopitems");
        return mjopitemRepository.findAll();
    }

    /**
     * {@code GET  /mjopitems/:id} : get the "id" mjopitem.
     *
     * @param id the id of the mjopitem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mjopitem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Mjopitem> getMjopitem(@PathVariable("id") Long id) {
        log.debug("REST request to get Mjopitem : {}", id);
        Optional<Mjopitem> mjopitem = mjopitemRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(mjopitem);
    }

    /**
     * {@code DELETE  /mjopitems/:id} : delete the "id" mjopitem.
     *
     * @param id the id of the mjopitem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMjopitem(@PathVariable("id") Long id) {
        log.debug("REST request to delete Mjopitem : {}", id);
        mjopitemRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
