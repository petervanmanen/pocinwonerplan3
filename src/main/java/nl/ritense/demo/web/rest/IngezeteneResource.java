package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Ingezetene;
import nl.ritense.demo.repository.IngezeteneRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Ingezetene}.
 */
@RestController
@RequestMapping("/api/ingezetenes")
@Transactional
public class IngezeteneResource {

    private final Logger log = LoggerFactory.getLogger(IngezeteneResource.class);

    private static final String ENTITY_NAME = "ingezetene";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IngezeteneRepository ingezeteneRepository;

    public IngezeteneResource(IngezeteneRepository ingezeteneRepository) {
        this.ingezeteneRepository = ingezeteneRepository;
    }

    /**
     * {@code POST  /ingezetenes} : Create a new ingezetene.
     *
     * @param ingezetene the ingezetene to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ingezetene, or with status {@code 400 (Bad Request)} if the ingezetene has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Ingezetene> createIngezetene(@RequestBody Ingezetene ingezetene) throws URISyntaxException {
        log.debug("REST request to save Ingezetene : {}", ingezetene);
        if (ingezetene.getId() != null) {
            throw new BadRequestAlertException("A new ingezetene cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ingezetene = ingezeteneRepository.save(ingezetene);
        return ResponseEntity.created(new URI("/api/ingezetenes/" + ingezetene.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, ingezetene.getId().toString()))
            .body(ingezetene);
    }

    /**
     * {@code PUT  /ingezetenes/:id} : Updates an existing ingezetene.
     *
     * @param id the id of the ingezetene to save.
     * @param ingezetene the ingezetene to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ingezetene,
     * or with status {@code 400 (Bad Request)} if the ingezetene is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ingezetene couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Ingezetene> updateIngezetene(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Ingezetene ingezetene
    ) throws URISyntaxException {
        log.debug("REST request to update Ingezetene : {}, {}", id, ingezetene);
        if (ingezetene.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ingezetene.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ingezeteneRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ingezetene = ingezeteneRepository.save(ingezetene);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ingezetene.getId().toString()))
            .body(ingezetene);
    }

    /**
     * {@code PATCH  /ingezetenes/:id} : Partial updates given fields of an existing ingezetene, field will ignore if it is null
     *
     * @param id the id of the ingezetene to save.
     * @param ingezetene the ingezetene to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ingezetene,
     * or with status {@code 400 (Bad Request)} if the ingezetene is not valid,
     * or with status {@code 404 (Not Found)} if the ingezetene is not found,
     * or with status {@code 500 (Internal Server Error)} if the ingezetene couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Ingezetene> partialUpdateIngezetene(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Ingezetene ingezetene
    ) throws URISyntaxException {
        log.debug("REST request to partial update Ingezetene partially : {}, {}", id, ingezetene);
        if (ingezetene.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ingezetene.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ingezeteneRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Ingezetene> result = ingezeteneRepository
            .findById(ingezetene.getId())
            .map(existingIngezetene -> {
                if (ingezetene.getAanduidingeuropeeskiesrecht() != null) {
                    existingIngezetene.setAanduidingeuropeeskiesrecht(ingezetene.getAanduidingeuropeeskiesrecht());
                }
                if (ingezetene.getAanduidinguitgeslotenkiesrecht() != null) {
                    existingIngezetene.setAanduidinguitgeslotenkiesrecht(ingezetene.getAanduidinguitgeslotenkiesrecht());
                }
                if (ingezetene.getDatumverkrijgingverblijfstitel() != null) {
                    existingIngezetene.setDatumverkrijgingverblijfstitel(ingezetene.getDatumverkrijgingverblijfstitel());
                }
                if (ingezetene.getDatumverliesverblijfstitel() != null) {
                    existingIngezetene.setDatumverliesverblijfstitel(ingezetene.getDatumverliesverblijfstitel());
                }
                if (ingezetene.getIndicatieblokkering() != null) {
                    existingIngezetene.setIndicatieblokkering(ingezetene.getIndicatieblokkering());
                }
                if (ingezetene.getIndicatiecurateleregister() != null) {
                    existingIngezetene.setIndicatiecurateleregister(ingezetene.getIndicatiecurateleregister());
                }
                if (ingezetene.getIndicatiegezagminderjarige() != null) {
                    existingIngezetene.setIndicatiegezagminderjarige(ingezetene.getIndicatiegezagminderjarige());
                }

                return existingIngezetene;
            })
            .map(ingezeteneRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ingezetene.getId().toString())
        );
    }

    /**
     * {@code GET  /ingezetenes} : get all the ingezetenes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ingezetenes in body.
     */
    @GetMapping("")
    public List<Ingezetene> getAllIngezetenes() {
        log.debug("REST request to get all Ingezetenes");
        return ingezeteneRepository.findAll();
    }

    /**
     * {@code GET  /ingezetenes/:id} : get the "id" ingezetene.
     *
     * @param id the id of the ingezetene to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ingezetene, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Ingezetene> getIngezetene(@PathVariable("id") Long id) {
        log.debug("REST request to get Ingezetene : {}", id);
        Optional<Ingezetene> ingezetene = ingezeteneRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ingezetene);
    }

    /**
     * {@code DELETE  /ingezetenes/:id} : delete the "id" ingezetene.
     *
     * @param id the id of the ingezetene to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngezetene(@PathVariable("id") Long id) {
        log.debug("REST request to delete Ingezetene : {}", id);
        ingezeteneRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
