package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Vulling;
import nl.ritense.demo.repository.VullingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Vulling}.
 */
@RestController
@RequestMapping("/api/vullings")
@Transactional
public class VullingResource {

    private final Logger log = LoggerFactory.getLogger(VullingResource.class);

    private static final String ENTITY_NAME = "vulling";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VullingRepository vullingRepository;

    public VullingResource(VullingRepository vullingRepository) {
        this.vullingRepository = vullingRepository;
    }

    /**
     * {@code POST  /vullings} : Create a new vulling.
     *
     * @param vulling the vulling to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vulling, or with status {@code 400 (Bad Request)} if the vulling has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Vulling> createVulling(@Valid @RequestBody Vulling vulling) throws URISyntaxException {
        log.debug("REST request to save Vulling : {}", vulling);
        if (vulling.getId() != null) {
            throw new BadRequestAlertException("A new vulling cannot already have an ID", ENTITY_NAME, "idexists");
        }
        vulling = vullingRepository.save(vulling);
        return ResponseEntity.created(new URI("/api/vullings/" + vulling.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, vulling.getId().toString()))
            .body(vulling);
    }

    /**
     * {@code PUT  /vullings/:id} : Updates an existing vulling.
     *
     * @param id the id of the vulling to save.
     * @param vulling the vulling to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vulling,
     * or with status {@code 400 (Bad Request)} if the vulling is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vulling couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Vulling> updateVulling(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Vulling vulling
    ) throws URISyntaxException {
        log.debug("REST request to update Vulling : {}, {}", id, vulling);
        if (vulling.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vulling.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vullingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        vulling = vullingRepository.save(vulling);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vulling.getId().toString()))
            .body(vulling);
    }

    /**
     * {@code PATCH  /vullings/:id} : Partial updates given fields of an existing vulling, field will ignore if it is null
     *
     * @param id the id of the vulling to save.
     * @param vulling the vulling to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vulling,
     * or with status {@code 400 (Bad Request)} if the vulling is not valid,
     * or with status {@code 404 (Not Found)} if the vulling is not found,
     * or with status {@code 500 (Internal Server Error)} if the vulling couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Vulling> partialUpdateVulling(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Vulling vulling
    ) throws URISyntaxException {
        log.debug("REST request to partial update Vulling partially : {}, {}", id, vulling);
        if (vulling.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vulling.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vullingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Vulling> result = vullingRepository
            .findById(vulling.getId())
            .map(existingVulling -> {
                if (vulling.getGrondsoort() != null) {
                    existingVulling.setGrondsoort(vulling.getGrondsoort());
                }
                if (vulling.getKey() != null) {
                    existingVulling.setKey(vulling.getKey());
                }
                if (vulling.getKeyspoor() != null) {
                    existingVulling.setKeyspoor(vulling.getKeyspoor());
                }
                if (vulling.getKleur() != null) {
                    existingVulling.setKleur(vulling.getKleur());
                }
                if (vulling.getProjectcd() != null) {
                    existingVulling.setProjectcd(vulling.getProjectcd());
                }
                if (vulling.getPutnummer() != null) {
                    existingVulling.setPutnummer(vulling.getPutnummer());
                }
                if (vulling.getSpoornummer() != null) {
                    existingVulling.setSpoornummer(vulling.getSpoornummer());
                }
                if (vulling.getStructuur() != null) {
                    existingVulling.setStructuur(vulling.getStructuur());
                }
                if (vulling.getVlaknummer() != null) {
                    existingVulling.setVlaknummer(vulling.getVlaknummer());
                }
                if (vulling.getVullingnummer() != null) {
                    existingVulling.setVullingnummer(vulling.getVullingnummer());
                }

                return existingVulling;
            })
            .map(vullingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vulling.getId().toString())
        );
    }

    /**
     * {@code GET  /vullings} : get all the vullings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vullings in body.
     */
    @GetMapping("")
    public List<Vulling> getAllVullings() {
        log.debug("REST request to get all Vullings");
        return vullingRepository.findAll();
    }

    /**
     * {@code GET  /vullings/:id} : get the "id" vulling.
     *
     * @param id the id of the vulling to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vulling, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Vulling> getVulling(@PathVariable("id") Long id) {
        log.debug("REST request to get Vulling : {}", id);
        Optional<Vulling> vulling = vullingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(vulling);
    }

    /**
     * {@code DELETE  /vullings/:id} : delete the "id" vulling.
     *
     * @param id the id of the vulling to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVulling(@PathVariable("id") Long id) {
        log.debug("REST request to delete Vulling : {}", id);
        vullingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
