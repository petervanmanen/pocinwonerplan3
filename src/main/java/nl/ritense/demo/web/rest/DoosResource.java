package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Doos;
import nl.ritense.demo.repository.DoosRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Doos}.
 */
@RestController
@RequestMapping("/api/doos")
@Transactional
public class DoosResource {

    private final Logger log = LoggerFactory.getLogger(DoosResource.class);

    private static final String ENTITY_NAME = "doos";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DoosRepository doosRepository;

    public DoosResource(DoosRepository doosRepository) {
        this.doosRepository = doosRepository;
    }

    /**
     * {@code POST  /doos} : Create a new doos.
     *
     * @param doos the doos to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new doos, or with status {@code 400 (Bad Request)} if the doos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Doos> createDoos(@RequestBody Doos doos) throws URISyntaxException {
        log.debug("REST request to save Doos : {}", doos);
        if (doos.getId() != null) {
            throw new BadRequestAlertException("A new doos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        doos = doosRepository.save(doos);
        return ResponseEntity.created(new URI("/api/doos/" + doos.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, doos.getId().toString()))
            .body(doos);
    }

    /**
     * {@code PUT  /doos/:id} : Updates an existing doos.
     *
     * @param id the id of the doos to save.
     * @param doos the doos to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated doos,
     * or with status {@code 400 (Bad Request)} if the doos is not valid,
     * or with status {@code 500 (Internal Server Error)} if the doos couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Doos> updateDoos(@PathVariable(value = "id", required = false) final Long id, @RequestBody Doos doos)
        throws URISyntaxException {
        log.debug("REST request to update Doos : {}, {}", id, doos);
        if (doos.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, doos.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!doosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        doos = doosRepository.save(doos);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, doos.getId().toString()))
            .body(doos);
    }

    /**
     * {@code PATCH  /doos/:id} : Partial updates given fields of an existing doos, field will ignore if it is null
     *
     * @param id the id of the doos to save.
     * @param doos the doos to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated doos,
     * or with status {@code 400 (Bad Request)} if the doos is not valid,
     * or with status {@code 404 (Not Found)} if the doos is not found,
     * or with status {@code 500 (Internal Server Error)} if the doos couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Doos> partialUpdateDoos(@PathVariable(value = "id", required = false) final Long id, @RequestBody Doos doos)
        throws URISyntaxException {
        log.debug("REST request to partial update Doos partially : {}, {}", id, doos);
        if (doos.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, doos.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!doosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Doos> result = doosRepository
            .findById(doos.getId())
            .map(existingDoos -> {
                if (doos.getDoosnummer() != null) {
                    existingDoos.setDoosnummer(doos.getDoosnummer());
                }
                if (doos.getHerkomst() != null) {
                    existingDoos.setHerkomst(doos.getHerkomst());
                }
                if (doos.getInhoud() != null) {
                    existingDoos.setInhoud(doos.getInhoud());
                }
                if (doos.getKey() != null) {
                    existingDoos.setKey(doos.getKey());
                }
                if (doos.getKeymagazijnlocatie() != null) {
                    existingDoos.setKeymagazijnlocatie(doos.getKeymagazijnlocatie());
                }
                if (doos.getProjectcd() != null) {
                    existingDoos.setProjectcd(doos.getProjectcd());
                }

                return existingDoos;
            })
            .map(doosRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, doos.getId().toString())
        );
    }

    /**
     * {@code GET  /doos} : get all the doos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of doos in body.
     */
    @GetMapping("")
    public List<Doos> getAllDoos() {
        log.debug("REST request to get all Doos");
        return doosRepository.findAll();
    }

    /**
     * {@code GET  /doos/:id} : get the "id" doos.
     *
     * @param id the id of the doos to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the doos, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Doos> getDoos(@PathVariable("id") Long id) {
        log.debug("REST request to get Doos : {}", id);
        Optional<Doos> doos = doosRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(doos);
    }

    /**
     * {@code DELETE  /doos/:id} : delete the "id" doos.
     *
     * @param id the id of the doos to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoos(@PathVariable("id") Long id) {
        log.debug("REST request to delete Doos : {}", id);
        doosRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
