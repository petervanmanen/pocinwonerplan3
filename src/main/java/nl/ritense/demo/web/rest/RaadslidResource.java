package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import nl.ritense.demo.domain.Raadslid;
import nl.ritense.demo.repository.RaadslidRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Raadslid}.
 */
@RestController
@RequestMapping("/api/raadslids")
@Transactional
public class RaadslidResource {

    private final Logger log = LoggerFactory.getLogger(RaadslidResource.class);

    private static final String ENTITY_NAME = "raadslid";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RaadslidRepository raadslidRepository;

    public RaadslidResource(RaadslidRepository raadslidRepository) {
        this.raadslidRepository = raadslidRepository;
    }

    /**
     * {@code POST  /raadslids} : Create a new raadslid.
     *
     * @param raadslid the raadslid to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new raadslid, or with status {@code 400 (Bad Request)} if the raadslid has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Raadslid> createRaadslid(@RequestBody Raadslid raadslid) throws URISyntaxException {
        log.debug("REST request to save Raadslid : {}", raadslid);
        if (raadslid.getId() != null) {
            throw new BadRequestAlertException("A new raadslid cannot already have an ID", ENTITY_NAME, "idexists");
        }
        raadslid = raadslidRepository.save(raadslid);
        return ResponseEntity.created(new URI("/api/raadslids/" + raadslid.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, raadslid.getId().toString()))
            .body(raadslid);
    }

    /**
     * {@code PUT  /raadslids/:id} : Updates an existing raadslid.
     *
     * @param id the id of the raadslid to save.
     * @param raadslid the raadslid to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated raadslid,
     * or with status {@code 400 (Bad Request)} if the raadslid is not valid,
     * or with status {@code 500 (Internal Server Error)} if the raadslid couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Raadslid> updateRaadslid(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Raadslid raadslid
    ) throws URISyntaxException {
        log.debug("REST request to update Raadslid : {}, {}", id, raadslid);
        if (raadslid.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, raadslid.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!raadslidRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        raadslid = raadslidRepository.save(raadslid);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, raadslid.getId().toString()))
            .body(raadslid);
    }

    /**
     * {@code PATCH  /raadslids/:id} : Partial updates given fields of an existing raadslid, field will ignore if it is null
     *
     * @param id the id of the raadslid to save.
     * @param raadslid the raadslid to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated raadslid,
     * or with status {@code 400 (Bad Request)} if the raadslid is not valid,
     * or with status {@code 404 (Not Found)} if the raadslid is not found,
     * or with status {@code 500 (Internal Server Error)} if the raadslid couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Raadslid> partialUpdateRaadslid(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Raadslid raadslid
    ) throws URISyntaxException {
        log.debug("REST request to partial update Raadslid partially : {}, {}", id, raadslid);
        if (raadslid.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, raadslid.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!raadslidRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Raadslid> result = raadslidRepository
            .findById(raadslid.getId())
            .map(existingRaadslid -> {
                if (raadslid.getAchternaam() != null) {
                    existingRaadslid.setAchternaam(raadslid.getAchternaam());
                }
                if (raadslid.getDatumaanstelling() != null) {
                    existingRaadslid.setDatumaanstelling(raadslid.getDatumaanstelling());
                }
                if (raadslid.getDatumuittreding() != null) {
                    existingRaadslid.setDatumuittreding(raadslid.getDatumuittreding());
                }
                if (raadslid.getFractie() != null) {
                    existingRaadslid.setFractie(raadslid.getFractie());
                }
                if (raadslid.getTitel() != null) {
                    existingRaadslid.setTitel(raadslid.getTitel());
                }
                if (raadslid.getVoornaam() != null) {
                    existingRaadslid.setVoornaam(raadslid.getVoornaam());
                }

                return existingRaadslid;
            })
            .map(raadslidRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, raadslid.getId().toString())
        );
    }

    /**
     * {@code GET  /raadslids} : get all the raadslids.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of raadslids in body.
     */
    @GetMapping("")
    public List<Raadslid> getAllRaadslids(
        @RequestParam(name = "filter", required = false) String filter,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        if ("isindiener-is-null".equals(filter)) {
            log.debug("REST request to get all Raadslids where isIndiener is null");
            return StreamSupport.stream(raadslidRepository.findAll().spliterator(), false)
                .filter(raadslid -> raadslid.getIsIndiener() == null)
                .toList();
        }
        log.debug("REST request to get all Raadslids");
        if (eagerload) {
            return raadslidRepository.findAllWithEagerRelationships();
        } else {
            return raadslidRepository.findAll();
        }
    }

    /**
     * {@code GET  /raadslids/:id} : get the "id" raadslid.
     *
     * @param id the id of the raadslid to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the raadslid, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Raadslid> getRaadslid(@PathVariable("id") Long id) {
        log.debug("REST request to get Raadslid : {}", id);
        Optional<Raadslid> raadslid = raadslidRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(raadslid);
    }

    /**
     * {@code DELETE  /raadslids/:id} : delete the "id" raadslid.
     *
     * @param id the id of the raadslid to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRaadslid(@PathVariable("id") Long id) {
        log.debug("REST request to delete Raadslid : {}", id);
        raadslidRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
