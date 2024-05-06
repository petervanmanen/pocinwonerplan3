package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Vulgraadmeting;
import nl.ritense.demo.repository.VulgraadmetingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Vulgraadmeting}.
 */
@RestController
@RequestMapping("/api/vulgraadmetings")
@Transactional
public class VulgraadmetingResource {

    private final Logger log = LoggerFactory.getLogger(VulgraadmetingResource.class);

    private static final String ENTITY_NAME = "vulgraadmeting";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VulgraadmetingRepository vulgraadmetingRepository;

    public VulgraadmetingResource(VulgraadmetingRepository vulgraadmetingRepository) {
        this.vulgraadmetingRepository = vulgraadmetingRepository;
    }

    /**
     * {@code POST  /vulgraadmetings} : Create a new vulgraadmeting.
     *
     * @param vulgraadmeting the vulgraadmeting to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vulgraadmeting, or with status {@code 400 (Bad Request)} if the vulgraadmeting has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Vulgraadmeting> createVulgraadmeting(@RequestBody Vulgraadmeting vulgraadmeting) throws URISyntaxException {
        log.debug("REST request to save Vulgraadmeting : {}", vulgraadmeting);
        if (vulgraadmeting.getId() != null) {
            throw new BadRequestAlertException("A new vulgraadmeting cannot already have an ID", ENTITY_NAME, "idexists");
        }
        vulgraadmeting = vulgraadmetingRepository.save(vulgraadmeting);
        return ResponseEntity.created(new URI("/api/vulgraadmetings/" + vulgraadmeting.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, vulgraadmeting.getId().toString()))
            .body(vulgraadmeting);
    }

    /**
     * {@code PUT  /vulgraadmetings/:id} : Updates an existing vulgraadmeting.
     *
     * @param id the id of the vulgraadmeting to save.
     * @param vulgraadmeting the vulgraadmeting to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vulgraadmeting,
     * or with status {@code 400 (Bad Request)} if the vulgraadmeting is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vulgraadmeting couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Vulgraadmeting> updateVulgraadmeting(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Vulgraadmeting vulgraadmeting
    ) throws URISyntaxException {
        log.debug("REST request to update Vulgraadmeting : {}, {}", id, vulgraadmeting);
        if (vulgraadmeting.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vulgraadmeting.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vulgraadmetingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        vulgraadmeting = vulgraadmetingRepository.save(vulgraadmeting);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vulgraadmeting.getId().toString()))
            .body(vulgraadmeting);
    }

    /**
     * {@code PATCH  /vulgraadmetings/:id} : Partial updates given fields of an existing vulgraadmeting, field will ignore if it is null
     *
     * @param id the id of the vulgraadmeting to save.
     * @param vulgraadmeting the vulgraadmeting to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vulgraadmeting,
     * or with status {@code 400 (Bad Request)} if the vulgraadmeting is not valid,
     * or with status {@code 404 (Not Found)} if the vulgraadmeting is not found,
     * or with status {@code 500 (Internal Server Error)} if the vulgraadmeting couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Vulgraadmeting> partialUpdateVulgraadmeting(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Vulgraadmeting vulgraadmeting
    ) throws URISyntaxException {
        log.debug("REST request to partial update Vulgraadmeting partially : {}, {}", id, vulgraadmeting);
        if (vulgraadmeting.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vulgraadmeting.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vulgraadmetingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Vulgraadmeting> result = vulgraadmetingRepository
            .findById(vulgraadmeting.getId())
            .map(existingVulgraadmeting -> {
                if (vulgraadmeting.getTijdstip() != null) {
                    existingVulgraadmeting.setTijdstip(vulgraadmeting.getTijdstip());
                }
                if (vulgraadmeting.getVulgraad() != null) {
                    existingVulgraadmeting.setVulgraad(vulgraadmeting.getVulgraad());
                }
                if (vulgraadmeting.getVullinggewicht() != null) {
                    existingVulgraadmeting.setVullinggewicht(vulgraadmeting.getVullinggewicht());
                }

                return existingVulgraadmeting;
            })
            .map(vulgraadmetingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vulgraadmeting.getId().toString())
        );
    }

    /**
     * {@code GET  /vulgraadmetings} : get all the vulgraadmetings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vulgraadmetings in body.
     */
    @GetMapping("")
    public List<Vulgraadmeting> getAllVulgraadmetings() {
        log.debug("REST request to get all Vulgraadmetings");
        return vulgraadmetingRepository.findAll();
    }

    /**
     * {@code GET  /vulgraadmetings/:id} : get the "id" vulgraadmeting.
     *
     * @param id the id of the vulgraadmeting to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vulgraadmeting, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Vulgraadmeting> getVulgraadmeting(@PathVariable("id") Long id) {
        log.debug("REST request to get Vulgraadmeting : {}", id);
        Optional<Vulgraadmeting> vulgraadmeting = vulgraadmetingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(vulgraadmeting);
    }

    /**
     * {@code DELETE  /vulgraadmetings/:id} : delete the "id" vulgraadmeting.
     *
     * @param id the id of the vulgraadmeting to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVulgraadmeting(@PathVariable("id") Long id) {
        log.debug("REST request to delete Vulgraadmeting : {}", id);
        vulgraadmetingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
