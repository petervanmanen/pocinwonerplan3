package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Ontbindinghuwelijkgeregistreerdpartnerschap;
import nl.ritense.demo.repository.OntbindinghuwelijkgeregistreerdpartnerschapRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Ontbindinghuwelijkgeregistreerdpartnerschap}.
 */
@RestController
@RequestMapping("/api/ontbindinghuwelijkgeregistreerdpartnerschaps")
@Transactional
public class OntbindinghuwelijkgeregistreerdpartnerschapResource {

    private final Logger log = LoggerFactory.getLogger(OntbindinghuwelijkgeregistreerdpartnerschapResource.class);

    private static final String ENTITY_NAME = "ontbindinghuwelijkgeregistreerdpartnerschap";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OntbindinghuwelijkgeregistreerdpartnerschapRepository ontbindinghuwelijkgeregistreerdpartnerschapRepository;

    public OntbindinghuwelijkgeregistreerdpartnerschapResource(
        OntbindinghuwelijkgeregistreerdpartnerschapRepository ontbindinghuwelijkgeregistreerdpartnerschapRepository
    ) {
        this.ontbindinghuwelijkgeregistreerdpartnerschapRepository = ontbindinghuwelijkgeregistreerdpartnerschapRepository;
    }

    /**
     * {@code POST  /ontbindinghuwelijkgeregistreerdpartnerschaps} : Create a new ontbindinghuwelijkgeregistreerdpartnerschap.
     *
     * @param ontbindinghuwelijkgeregistreerdpartnerschap the ontbindinghuwelijkgeregistreerdpartnerschap to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ontbindinghuwelijkgeregistreerdpartnerschap, or with status {@code 400 (Bad Request)} if the ontbindinghuwelijkgeregistreerdpartnerschap has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Ontbindinghuwelijkgeregistreerdpartnerschap> createOntbindinghuwelijkgeregistreerdpartnerschap(
        @RequestBody Ontbindinghuwelijkgeregistreerdpartnerschap ontbindinghuwelijkgeregistreerdpartnerschap
    ) throws URISyntaxException {
        log.debug("REST request to save Ontbindinghuwelijkgeregistreerdpartnerschap : {}", ontbindinghuwelijkgeregistreerdpartnerschap);
        if (ontbindinghuwelijkgeregistreerdpartnerschap.getId() != null) {
            throw new BadRequestAlertException(
                "A new ontbindinghuwelijkgeregistreerdpartnerschap cannot already have an ID",
                ENTITY_NAME,
                "idexists"
            );
        }
        ontbindinghuwelijkgeregistreerdpartnerschap = ontbindinghuwelijkgeregistreerdpartnerschapRepository.save(
            ontbindinghuwelijkgeregistreerdpartnerschap
        );
        return ResponseEntity.created(
            new URI("/api/ontbindinghuwelijkgeregistreerdpartnerschaps/" + ontbindinghuwelijkgeregistreerdpartnerschap.getId())
        )
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    ontbindinghuwelijkgeregistreerdpartnerschap.getId().toString()
                )
            )
            .body(ontbindinghuwelijkgeregistreerdpartnerschap);
    }

    /**
     * {@code PUT  /ontbindinghuwelijkgeregistreerdpartnerschaps/:id} : Updates an existing ontbindinghuwelijkgeregistreerdpartnerschap.
     *
     * @param id the id of the ontbindinghuwelijkgeregistreerdpartnerschap to save.
     * @param ontbindinghuwelijkgeregistreerdpartnerschap the ontbindinghuwelijkgeregistreerdpartnerschap to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ontbindinghuwelijkgeregistreerdpartnerschap,
     * or with status {@code 400 (Bad Request)} if the ontbindinghuwelijkgeregistreerdpartnerschap is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ontbindinghuwelijkgeregistreerdpartnerschap couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Ontbindinghuwelijkgeregistreerdpartnerschap> updateOntbindinghuwelijkgeregistreerdpartnerschap(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Ontbindinghuwelijkgeregistreerdpartnerschap ontbindinghuwelijkgeregistreerdpartnerschap
    ) throws URISyntaxException {
        log.debug(
            "REST request to update Ontbindinghuwelijkgeregistreerdpartnerschap : {}, {}",
            id,
            ontbindinghuwelijkgeregistreerdpartnerschap
        );
        if (ontbindinghuwelijkgeregistreerdpartnerschap.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ontbindinghuwelijkgeregistreerdpartnerschap.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ontbindinghuwelijkgeregistreerdpartnerschapRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ontbindinghuwelijkgeregistreerdpartnerschap = ontbindinghuwelijkgeregistreerdpartnerschapRepository.save(
            ontbindinghuwelijkgeregistreerdpartnerschap
        );
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    ontbindinghuwelijkgeregistreerdpartnerschap.getId().toString()
                )
            )
            .body(ontbindinghuwelijkgeregistreerdpartnerschap);
    }

    /**
     * {@code PATCH  /ontbindinghuwelijkgeregistreerdpartnerschaps/:id} : Partial updates given fields of an existing ontbindinghuwelijkgeregistreerdpartnerschap, field will ignore if it is null
     *
     * @param id the id of the ontbindinghuwelijkgeregistreerdpartnerschap to save.
     * @param ontbindinghuwelijkgeregistreerdpartnerschap the ontbindinghuwelijkgeregistreerdpartnerschap to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ontbindinghuwelijkgeregistreerdpartnerschap,
     * or with status {@code 400 (Bad Request)} if the ontbindinghuwelijkgeregistreerdpartnerschap is not valid,
     * or with status {@code 404 (Not Found)} if the ontbindinghuwelijkgeregistreerdpartnerschap is not found,
     * or with status {@code 500 (Internal Server Error)} if the ontbindinghuwelijkgeregistreerdpartnerschap couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Ontbindinghuwelijkgeregistreerdpartnerschap> partialUpdateOntbindinghuwelijkgeregistreerdpartnerschap(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Ontbindinghuwelijkgeregistreerdpartnerschap ontbindinghuwelijkgeregistreerdpartnerschap
    ) throws URISyntaxException {
        log.debug(
            "REST request to partial update Ontbindinghuwelijkgeregistreerdpartnerschap partially : {}, {}",
            id,
            ontbindinghuwelijkgeregistreerdpartnerschap
        );
        if (ontbindinghuwelijkgeregistreerdpartnerschap.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ontbindinghuwelijkgeregistreerdpartnerschap.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ontbindinghuwelijkgeregistreerdpartnerschapRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Ontbindinghuwelijkgeregistreerdpartnerschap> result = ontbindinghuwelijkgeregistreerdpartnerschapRepository
            .findById(ontbindinghuwelijkgeregistreerdpartnerschap.getId())
            .map(existingOntbindinghuwelijkgeregistreerdpartnerschap -> {
                if (ontbindinghuwelijkgeregistreerdpartnerschap.getBuitenlandseplaatseinde() != null) {
                    existingOntbindinghuwelijkgeregistreerdpartnerschap.setBuitenlandseplaatseinde(
                        ontbindinghuwelijkgeregistreerdpartnerschap.getBuitenlandseplaatseinde()
                    );
                }
                if (ontbindinghuwelijkgeregistreerdpartnerschap.getBuitenlandseregioeinde() != null) {
                    existingOntbindinghuwelijkgeregistreerdpartnerschap.setBuitenlandseregioeinde(
                        ontbindinghuwelijkgeregistreerdpartnerschap.getBuitenlandseregioeinde()
                    );
                }
                if (ontbindinghuwelijkgeregistreerdpartnerschap.getDatumeinde() != null) {
                    existingOntbindinghuwelijkgeregistreerdpartnerschap.setDatumeinde(
                        ontbindinghuwelijkgeregistreerdpartnerschap.getDatumeinde()
                    );
                }
                if (ontbindinghuwelijkgeregistreerdpartnerschap.getGemeenteeinde() != null) {
                    existingOntbindinghuwelijkgeregistreerdpartnerschap.setGemeenteeinde(
                        ontbindinghuwelijkgeregistreerdpartnerschap.getGemeenteeinde()
                    );
                }
                if (ontbindinghuwelijkgeregistreerdpartnerschap.getLandofgebiedeinde() != null) {
                    existingOntbindinghuwelijkgeregistreerdpartnerschap.setLandofgebiedeinde(
                        ontbindinghuwelijkgeregistreerdpartnerschap.getLandofgebiedeinde()
                    );
                }
                if (ontbindinghuwelijkgeregistreerdpartnerschap.getOmschrijvinglocatieeinde() != null) {
                    existingOntbindinghuwelijkgeregistreerdpartnerschap.setOmschrijvinglocatieeinde(
                        ontbindinghuwelijkgeregistreerdpartnerschap.getOmschrijvinglocatieeinde()
                    );
                }
                if (ontbindinghuwelijkgeregistreerdpartnerschap.getRedeneinde() != null) {
                    existingOntbindinghuwelijkgeregistreerdpartnerschap.setRedeneinde(
                        ontbindinghuwelijkgeregistreerdpartnerschap.getRedeneinde()
                    );
                }

                return existingOntbindinghuwelijkgeregistreerdpartnerschap;
            })
            .map(ontbindinghuwelijkgeregistreerdpartnerschapRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(
                applicationName,
                false,
                ENTITY_NAME,
                ontbindinghuwelijkgeregistreerdpartnerschap.getId().toString()
            )
        );
    }

    /**
     * {@code GET  /ontbindinghuwelijkgeregistreerdpartnerschaps} : get all the ontbindinghuwelijkgeregistreerdpartnerschaps.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ontbindinghuwelijkgeregistreerdpartnerschaps in body.
     */
    @GetMapping("")
    public List<Ontbindinghuwelijkgeregistreerdpartnerschap> getAllOntbindinghuwelijkgeregistreerdpartnerschaps() {
        log.debug("REST request to get all Ontbindinghuwelijkgeregistreerdpartnerschaps");
        return ontbindinghuwelijkgeregistreerdpartnerschapRepository.findAll();
    }

    /**
     * {@code GET  /ontbindinghuwelijkgeregistreerdpartnerschaps/:id} : get the "id" ontbindinghuwelijkgeregistreerdpartnerschap.
     *
     * @param id the id of the ontbindinghuwelijkgeregistreerdpartnerschap to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ontbindinghuwelijkgeregistreerdpartnerschap, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Ontbindinghuwelijkgeregistreerdpartnerschap> getOntbindinghuwelijkgeregistreerdpartnerschap(
        @PathVariable("id") Long id
    ) {
        log.debug("REST request to get Ontbindinghuwelijkgeregistreerdpartnerschap : {}", id);
        Optional<Ontbindinghuwelijkgeregistreerdpartnerschap> ontbindinghuwelijkgeregistreerdpartnerschap =
            ontbindinghuwelijkgeregistreerdpartnerschapRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ontbindinghuwelijkgeregistreerdpartnerschap);
    }

    /**
     * {@code DELETE  /ontbindinghuwelijkgeregistreerdpartnerschaps/:id} : delete the "id" ontbindinghuwelijkgeregistreerdpartnerschap.
     *
     * @param id the id of the ontbindinghuwelijkgeregistreerdpartnerschap to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOntbindinghuwelijkgeregistreerdpartnerschap(@PathVariable("id") Long id) {
        log.debug("REST request to delete Ontbindinghuwelijkgeregistreerdpartnerschap : {}", id);
        ontbindinghuwelijkgeregistreerdpartnerschapRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
