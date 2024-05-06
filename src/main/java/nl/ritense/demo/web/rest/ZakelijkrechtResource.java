package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Zakelijkrecht;
import nl.ritense.demo.repository.ZakelijkrechtRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Zakelijkrecht}.
 */
@RestController
@RequestMapping("/api/zakelijkrechts")
@Transactional
public class ZakelijkrechtResource {

    private final Logger log = LoggerFactory.getLogger(ZakelijkrechtResource.class);

    private static final String ENTITY_NAME = "zakelijkrecht";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ZakelijkrechtRepository zakelijkrechtRepository;

    public ZakelijkrechtResource(ZakelijkrechtRepository zakelijkrechtRepository) {
        this.zakelijkrechtRepository = zakelijkrechtRepository;
    }

    /**
     * {@code POST  /zakelijkrechts} : Create a new zakelijkrecht.
     *
     * @param zakelijkrecht the zakelijkrecht to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new zakelijkrecht, or with status {@code 400 (Bad Request)} if the zakelijkrecht has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Zakelijkrecht> createZakelijkrecht(@RequestBody Zakelijkrecht zakelijkrecht) throws URISyntaxException {
        log.debug("REST request to save Zakelijkrecht : {}", zakelijkrecht);
        if (zakelijkrecht.getId() != null) {
            throw new BadRequestAlertException("A new zakelijkrecht cannot already have an ID", ENTITY_NAME, "idexists");
        }
        zakelijkrecht = zakelijkrechtRepository.save(zakelijkrecht);
        return ResponseEntity.created(new URI("/api/zakelijkrechts/" + zakelijkrecht.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, zakelijkrecht.getId().toString()))
            .body(zakelijkrecht);
    }

    /**
     * {@code PUT  /zakelijkrechts/:id} : Updates an existing zakelijkrecht.
     *
     * @param id the id of the zakelijkrecht to save.
     * @param zakelijkrecht the zakelijkrecht to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated zakelijkrecht,
     * or with status {@code 400 (Bad Request)} if the zakelijkrecht is not valid,
     * or with status {@code 500 (Internal Server Error)} if the zakelijkrecht couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Zakelijkrecht> updateZakelijkrecht(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Zakelijkrecht zakelijkrecht
    ) throws URISyntaxException {
        log.debug("REST request to update Zakelijkrecht : {}, {}", id, zakelijkrecht);
        if (zakelijkrecht.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, zakelijkrecht.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!zakelijkrechtRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        zakelijkrecht = zakelijkrechtRepository.save(zakelijkrecht);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, zakelijkrecht.getId().toString()))
            .body(zakelijkrecht);
    }

    /**
     * {@code PATCH  /zakelijkrechts/:id} : Partial updates given fields of an existing zakelijkrecht, field will ignore if it is null
     *
     * @param id the id of the zakelijkrecht to save.
     * @param zakelijkrecht the zakelijkrecht to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated zakelijkrecht,
     * or with status {@code 400 (Bad Request)} if the zakelijkrecht is not valid,
     * or with status {@code 404 (Not Found)} if the zakelijkrecht is not found,
     * or with status {@code 500 (Internal Server Error)} if the zakelijkrecht couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Zakelijkrecht> partialUpdateZakelijkrecht(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Zakelijkrecht zakelijkrecht
    ) throws URISyntaxException {
        log.debug("REST request to partial update Zakelijkrecht partially : {}, {}", id, zakelijkrecht);
        if (zakelijkrecht.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, zakelijkrecht.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!zakelijkrechtRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Zakelijkrecht> result = zakelijkrechtRepository
            .findById(zakelijkrecht.getId())
            .map(existingZakelijkrecht -> {
                if (zakelijkrecht.getDatumeinde() != null) {
                    existingZakelijkrecht.setDatumeinde(zakelijkrecht.getDatumeinde());
                }
                if (zakelijkrecht.getDatumstart() != null) {
                    existingZakelijkrecht.setDatumstart(zakelijkrecht.getDatumstart());
                }
                if (zakelijkrecht.getKosten() != null) {
                    existingZakelijkrecht.setKosten(zakelijkrecht.getKosten());
                }
                if (zakelijkrecht.getSoort() != null) {
                    existingZakelijkrecht.setSoort(zakelijkrecht.getSoort());
                }

                return existingZakelijkrecht;
            })
            .map(zakelijkrechtRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, zakelijkrecht.getId().toString())
        );
    }

    /**
     * {@code GET  /zakelijkrechts} : get all the zakelijkrechts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of zakelijkrechts in body.
     */
    @GetMapping("")
    public List<Zakelijkrecht> getAllZakelijkrechts() {
        log.debug("REST request to get all Zakelijkrechts");
        return zakelijkrechtRepository.findAll();
    }

    /**
     * {@code GET  /zakelijkrechts/:id} : get the "id" zakelijkrecht.
     *
     * @param id the id of the zakelijkrecht to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the zakelijkrecht, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Zakelijkrecht> getZakelijkrecht(@PathVariable("id") Long id) {
        log.debug("REST request to get Zakelijkrecht : {}", id);
        Optional<Zakelijkrecht> zakelijkrecht = zakelijkrechtRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(zakelijkrecht);
    }

    /**
     * {@code DELETE  /zakelijkrechts/:id} : delete the "id" zakelijkrecht.
     *
     * @param id the id of the zakelijkrecht to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteZakelijkrecht(@PathVariable("id") Long id) {
        log.debug("REST request to delete Zakelijkrecht : {}", id);
        zakelijkrechtRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
