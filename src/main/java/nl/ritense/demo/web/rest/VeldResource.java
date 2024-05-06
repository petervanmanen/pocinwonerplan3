package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Veld;
import nl.ritense.demo.repository.VeldRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Veld}.
 */
@RestController
@RequestMapping("/api/velds")
@Transactional
public class VeldResource {

    private final Logger log = LoggerFactory.getLogger(VeldResource.class);

    private static final String ENTITY_NAME = "veld";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VeldRepository veldRepository;

    public VeldResource(VeldRepository veldRepository) {
        this.veldRepository = veldRepository;
    }

    /**
     * {@code POST  /velds} : Create a new veld.
     *
     * @param veld the veld to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new veld, or with status {@code 400 (Bad Request)} if the veld has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Veld> createVeld(@RequestBody Veld veld) throws URISyntaxException {
        log.debug("REST request to save Veld : {}", veld);
        if (veld.getId() != null) {
            throw new BadRequestAlertException("A new veld cannot already have an ID", ENTITY_NAME, "idexists");
        }
        veld = veldRepository.save(veld);
        return ResponseEntity.created(new URI("/api/velds/" + veld.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, veld.getId().toString()))
            .body(veld);
    }

    /**
     * {@code PUT  /velds/:id} : Updates an existing veld.
     *
     * @param id the id of the veld to save.
     * @param veld the veld to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated veld,
     * or with status {@code 400 (Bad Request)} if the veld is not valid,
     * or with status {@code 500 (Internal Server Error)} if the veld couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Veld> updateVeld(@PathVariable(value = "id", required = false) final Long id, @RequestBody Veld veld)
        throws URISyntaxException {
        log.debug("REST request to update Veld : {}, {}", id, veld);
        if (veld.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, veld.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!veldRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        veld = veldRepository.save(veld);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, veld.getId().toString()))
            .body(veld);
    }

    /**
     * {@code PATCH  /velds/:id} : Partial updates given fields of an existing veld, field will ignore if it is null
     *
     * @param id the id of the veld to save.
     * @param veld the veld to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated veld,
     * or with status {@code 400 (Bad Request)} if the veld is not valid,
     * or with status {@code 404 (Not Found)} if the veld is not found,
     * or with status {@code 500 (Internal Server Error)} if the veld couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Veld> partialUpdateVeld(@PathVariable(value = "id", required = false) final Long id, @RequestBody Veld veld)
        throws URISyntaxException {
        log.debug("REST request to partial update Veld partially : {}, {}", id, veld);
        if (veld.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, veld.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!veldRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Veld> result = veldRepository.findById(veld.getId()).map(veldRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, veld.getId().toString())
        );
    }

    /**
     * {@code GET  /velds} : get all the velds.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of velds in body.
     */
    @GetMapping("")
    public List<Veld> getAllVelds(@RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload) {
        log.debug("REST request to get all Velds");
        if (eagerload) {
            return veldRepository.findAllWithEagerRelationships();
        } else {
            return veldRepository.findAll();
        }
    }

    /**
     * {@code GET  /velds/:id} : get the "id" veld.
     *
     * @param id the id of the veld to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the veld, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Veld> getVeld(@PathVariable("id") Long id) {
        log.debug("REST request to get Veld : {}", id);
        Optional<Veld> veld = veldRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(veld);
    }

    /**
     * {@code DELETE  /velds/:id} : delete the "id" veld.
     *
     * @param id the id of the veld to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVeld(@PathVariable("id") Long id) {
        log.debug("REST request to delete Veld : {}", id);
        veldRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
