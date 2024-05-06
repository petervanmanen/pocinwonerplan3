package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Nadaanvullingbrp;
import nl.ritense.demo.repository.NadaanvullingbrpRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Nadaanvullingbrp}.
 */
@RestController
@RequestMapping("/api/nadaanvullingbrps")
@Transactional
public class NadaanvullingbrpResource {

    private final Logger log = LoggerFactory.getLogger(NadaanvullingbrpResource.class);

    private static final String ENTITY_NAME = "nadaanvullingbrp";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NadaanvullingbrpRepository nadaanvullingbrpRepository;

    public NadaanvullingbrpResource(NadaanvullingbrpRepository nadaanvullingbrpRepository) {
        this.nadaanvullingbrpRepository = nadaanvullingbrpRepository;
    }

    /**
     * {@code POST  /nadaanvullingbrps} : Create a new nadaanvullingbrp.
     *
     * @param nadaanvullingbrp the nadaanvullingbrp to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nadaanvullingbrp, or with status {@code 400 (Bad Request)} if the nadaanvullingbrp has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Nadaanvullingbrp> createNadaanvullingbrp(@RequestBody Nadaanvullingbrp nadaanvullingbrp)
        throws URISyntaxException {
        log.debug("REST request to save Nadaanvullingbrp : {}", nadaanvullingbrp);
        if (nadaanvullingbrp.getId() != null) {
            throw new BadRequestAlertException("A new nadaanvullingbrp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        nadaanvullingbrp = nadaanvullingbrpRepository.save(nadaanvullingbrp);
        return ResponseEntity.created(new URI("/api/nadaanvullingbrps/" + nadaanvullingbrp.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, nadaanvullingbrp.getId().toString()))
            .body(nadaanvullingbrp);
    }

    /**
     * {@code PUT  /nadaanvullingbrps/:id} : Updates an existing nadaanvullingbrp.
     *
     * @param id the id of the nadaanvullingbrp to save.
     * @param nadaanvullingbrp the nadaanvullingbrp to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nadaanvullingbrp,
     * or with status {@code 400 (Bad Request)} if the nadaanvullingbrp is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nadaanvullingbrp couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Nadaanvullingbrp> updateNadaanvullingbrp(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Nadaanvullingbrp nadaanvullingbrp
    ) throws URISyntaxException {
        log.debug("REST request to update Nadaanvullingbrp : {}, {}", id, nadaanvullingbrp);
        if (nadaanvullingbrp.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nadaanvullingbrp.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nadaanvullingbrpRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        nadaanvullingbrp = nadaanvullingbrpRepository.save(nadaanvullingbrp);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, nadaanvullingbrp.getId().toString()))
            .body(nadaanvullingbrp);
    }

    /**
     * {@code PATCH  /nadaanvullingbrps/:id} : Partial updates given fields of an existing nadaanvullingbrp, field will ignore if it is null
     *
     * @param id the id of the nadaanvullingbrp to save.
     * @param nadaanvullingbrp the nadaanvullingbrp to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nadaanvullingbrp,
     * or with status {@code 400 (Bad Request)} if the nadaanvullingbrp is not valid,
     * or with status {@code 404 (Not Found)} if the nadaanvullingbrp is not found,
     * or with status {@code 500 (Internal Server Error)} if the nadaanvullingbrp couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Nadaanvullingbrp> partialUpdateNadaanvullingbrp(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Nadaanvullingbrp nadaanvullingbrp
    ) throws URISyntaxException {
        log.debug("REST request to partial update Nadaanvullingbrp partially : {}, {}", id, nadaanvullingbrp);
        if (nadaanvullingbrp.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nadaanvullingbrp.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nadaanvullingbrpRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Nadaanvullingbrp> result = nadaanvullingbrpRepository
            .findById(nadaanvullingbrp.getId())
            .map(existingNadaanvullingbrp -> {
                if (nadaanvullingbrp.getOpmerkingen() != null) {
                    existingNadaanvullingbrp.setOpmerkingen(nadaanvullingbrp.getOpmerkingen());
                }

                return existingNadaanvullingbrp;
            })
            .map(nadaanvullingbrpRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, nadaanvullingbrp.getId().toString())
        );
    }

    /**
     * {@code GET  /nadaanvullingbrps} : get all the nadaanvullingbrps.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nadaanvullingbrps in body.
     */
    @GetMapping("")
    public List<Nadaanvullingbrp> getAllNadaanvullingbrps() {
        log.debug("REST request to get all Nadaanvullingbrps");
        return nadaanvullingbrpRepository.findAll();
    }

    /**
     * {@code GET  /nadaanvullingbrps/:id} : get the "id" nadaanvullingbrp.
     *
     * @param id the id of the nadaanvullingbrp to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nadaanvullingbrp, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Nadaanvullingbrp> getNadaanvullingbrp(@PathVariable("id") Long id) {
        log.debug("REST request to get Nadaanvullingbrp : {}", id);
        Optional<Nadaanvullingbrp> nadaanvullingbrp = nadaanvullingbrpRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(nadaanvullingbrp);
    }

    /**
     * {@code DELETE  /nadaanvullingbrps/:id} : delete the "id" nadaanvullingbrp.
     *
     * @param id the id of the nadaanvullingbrp to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNadaanvullingbrp(@PathVariable("id") Long id) {
        log.debug("REST request to delete Nadaanvullingbrp : {}", id);
        nadaanvullingbrpRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
