package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Startformulieraanbesteden;
import nl.ritense.demo.repository.StartformulieraanbestedenRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Startformulieraanbesteden}.
 */
@RestController
@RequestMapping("/api/startformulieraanbestedens")
@Transactional
public class StartformulieraanbestedenResource {

    private final Logger log = LoggerFactory.getLogger(StartformulieraanbestedenResource.class);

    private static final String ENTITY_NAME = "startformulieraanbesteden";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StartformulieraanbestedenRepository startformulieraanbestedenRepository;

    public StartformulieraanbestedenResource(StartformulieraanbestedenRepository startformulieraanbestedenRepository) {
        this.startformulieraanbestedenRepository = startformulieraanbestedenRepository;
    }

    /**
     * {@code POST  /startformulieraanbestedens} : Create a new startformulieraanbesteden.
     *
     * @param startformulieraanbesteden the startformulieraanbesteden to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new startformulieraanbesteden, or with status {@code 400 (Bad Request)} if the startformulieraanbesteden has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Startformulieraanbesteden> createStartformulieraanbesteden(
        @RequestBody Startformulieraanbesteden startformulieraanbesteden
    ) throws URISyntaxException {
        log.debug("REST request to save Startformulieraanbesteden : {}", startformulieraanbesteden);
        if (startformulieraanbesteden.getId() != null) {
            throw new BadRequestAlertException("A new startformulieraanbesteden cannot already have an ID", ENTITY_NAME, "idexists");
        }
        startformulieraanbesteden = startformulieraanbestedenRepository.save(startformulieraanbesteden);
        return ResponseEntity.created(new URI("/api/startformulieraanbestedens/" + startformulieraanbesteden.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, startformulieraanbesteden.getId().toString())
            )
            .body(startformulieraanbesteden);
    }

    /**
     * {@code PUT  /startformulieraanbestedens/:id} : Updates an existing startformulieraanbesteden.
     *
     * @param id the id of the startformulieraanbesteden to save.
     * @param startformulieraanbesteden the startformulieraanbesteden to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated startformulieraanbesteden,
     * or with status {@code 400 (Bad Request)} if the startformulieraanbesteden is not valid,
     * or with status {@code 500 (Internal Server Error)} if the startformulieraanbesteden couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Startformulieraanbesteden> updateStartformulieraanbesteden(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Startformulieraanbesteden startformulieraanbesteden
    ) throws URISyntaxException {
        log.debug("REST request to update Startformulieraanbesteden : {}, {}", id, startformulieraanbesteden);
        if (startformulieraanbesteden.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, startformulieraanbesteden.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!startformulieraanbestedenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        startformulieraanbesteden = startformulieraanbestedenRepository.save(startformulieraanbesteden);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, startformulieraanbesteden.getId().toString()))
            .body(startformulieraanbesteden);
    }

    /**
     * {@code PATCH  /startformulieraanbestedens/:id} : Partial updates given fields of an existing startformulieraanbesteden, field will ignore if it is null
     *
     * @param id the id of the startformulieraanbesteden to save.
     * @param startformulieraanbesteden the startformulieraanbesteden to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated startformulieraanbesteden,
     * or with status {@code 400 (Bad Request)} if the startformulieraanbesteden is not valid,
     * or with status {@code 404 (Not Found)} if the startformulieraanbesteden is not found,
     * or with status {@code 500 (Internal Server Error)} if the startformulieraanbesteden couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Startformulieraanbesteden> partialUpdateStartformulieraanbesteden(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Startformulieraanbesteden startformulieraanbesteden
    ) throws URISyntaxException {
        log.debug("REST request to partial update Startformulieraanbesteden partially : {}, {}", id, startformulieraanbesteden);
        if (startformulieraanbesteden.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, startformulieraanbesteden.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!startformulieraanbestedenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Startformulieraanbesteden> result = startformulieraanbestedenRepository
            .findById(startformulieraanbesteden.getId())
            .map(existingStartformulieraanbesteden -> {
                if (startformulieraanbesteden.getBeoogdelooptijd() != null) {
                    existingStartformulieraanbesteden.setBeoogdelooptijd(startformulieraanbesteden.getBeoogdelooptijd());
                }
                if (startformulieraanbesteden.getBeoogdetotaleopdrachtwaarde() != null) {
                    existingStartformulieraanbesteden.setBeoogdetotaleopdrachtwaarde(
                        startformulieraanbesteden.getBeoogdetotaleopdrachtwaarde()
                    );
                }
                if (startformulieraanbesteden.getIndicatieaanvullendeopdrachtleverancier() != null) {
                    existingStartformulieraanbesteden.setIndicatieaanvullendeopdrachtleverancier(
                        startformulieraanbesteden.getIndicatieaanvullendeopdrachtleverancier()
                    );
                }
                if (startformulieraanbesteden.getIndicatiebeoogdeaanbestedingonderhands() != null) {
                    existingStartformulieraanbesteden.setIndicatiebeoogdeaanbestedingonderhands(
                        startformulieraanbesteden.getIndicatiebeoogdeaanbestedingonderhands()
                    );
                }
                if (startformulieraanbesteden.getIndicatiebeoogdeprockomtovereen() != null) {
                    existingStartformulieraanbesteden.setIndicatiebeoogdeprockomtovereen(
                        startformulieraanbesteden.getIndicatiebeoogdeprockomtovereen()
                    );
                }
                if (startformulieraanbesteden.getIndicatieeenmaligelos() != null) {
                    existingStartformulieraanbesteden.setIndicatieeenmaligelos(startformulieraanbesteden.getIndicatieeenmaligelos());
                }
                if (startformulieraanbesteden.getIndicatiemeerjarigeraamovereenkomst() != null) {
                    existingStartformulieraanbesteden.setIndicatiemeerjarigeraamovereenkomst(
                        startformulieraanbesteden.getIndicatiemeerjarigeraamovereenkomst()
                    );
                }
                if (startformulieraanbesteden.getIndicatiemeerjarigrepeterend() != null) {
                    existingStartformulieraanbesteden.setIndicatiemeerjarigrepeterend(
                        startformulieraanbesteden.getIndicatiemeerjarigrepeterend()
                    );
                }
                if (startformulieraanbesteden.getIndicatoroverkoepelendproject() != null) {
                    existingStartformulieraanbesteden.setIndicatoroverkoepelendproject(
                        startformulieraanbesteden.getIndicatoroverkoepelendproject()
                    );
                }
                if (startformulieraanbesteden.getOmschrijving() != null) {
                    existingStartformulieraanbesteden.setOmschrijving(startformulieraanbesteden.getOmschrijving());
                }
                if (startformulieraanbesteden.getOpdrachtcategorie() != null) {
                    existingStartformulieraanbesteden.setOpdrachtcategorie(startformulieraanbesteden.getOpdrachtcategorie());
                }
                if (startformulieraanbesteden.getOpdrachtsoort() != null) {
                    existingStartformulieraanbesteden.setOpdrachtsoort(startformulieraanbesteden.getOpdrachtsoort());
                }
                if (startformulieraanbesteden.getToelichtingaanvullendeopdracht() != null) {
                    existingStartformulieraanbesteden.setToelichtingaanvullendeopdracht(
                        startformulieraanbesteden.getToelichtingaanvullendeopdracht()
                    );
                }
                if (startformulieraanbesteden.getToelichtingeenmaligofrepeterend() != null) {
                    existingStartformulieraanbesteden.setToelichtingeenmaligofrepeterend(
                        startformulieraanbesteden.getToelichtingeenmaligofrepeterend()
                    );
                }

                return existingStartformulieraanbesteden;
            })
            .map(startformulieraanbestedenRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, startformulieraanbesteden.getId().toString())
        );
    }

    /**
     * {@code GET  /startformulieraanbestedens} : get all the startformulieraanbestedens.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of startformulieraanbestedens in body.
     */
    @GetMapping("")
    public List<Startformulieraanbesteden> getAllStartformulieraanbestedens() {
        log.debug("REST request to get all Startformulieraanbestedens");
        return startformulieraanbestedenRepository.findAll();
    }

    /**
     * {@code GET  /startformulieraanbestedens/:id} : get the "id" startformulieraanbesteden.
     *
     * @param id the id of the startformulieraanbesteden to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the startformulieraanbesteden, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Startformulieraanbesteden> getStartformulieraanbesteden(@PathVariable("id") Long id) {
        log.debug("REST request to get Startformulieraanbesteden : {}", id);
        Optional<Startformulieraanbesteden> startformulieraanbesteden = startformulieraanbestedenRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(startformulieraanbesteden);
    }

    /**
     * {@code DELETE  /startformulieraanbestedens/:id} : delete the "id" startformulieraanbesteden.
     *
     * @param id the id of the startformulieraanbesteden to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStartformulieraanbesteden(@PathVariable("id") Long id) {
        log.debug("REST request to delete Startformulieraanbesteden : {}", id);
        startformulieraanbestedenRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
