package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Historischerol;
import nl.ritense.demo.repository.HistorischerolRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Historischerol}.
 */
@RestController
@RequestMapping("/api/historischerols")
@Transactional
public class HistorischerolResource {

    private final Logger log = LoggerFactory.getLogger(HistorischerolResource.class);

    private static final String ENTITY_NAME = "historischerol";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HistorischerolRepository historischerolRepository;

    public HistorischerolResource(HistorischerolRepository historischerolRepository) {
        this.historischerolRepository = historischerolRepository;
    }

    /**
     * {@code POST  /historischerols} : Create a new historischerol.
     *
     * @param historischerol the historischerol to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new historischerol, or with status {@code 400 (Bad Request)} if the historischerol has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Historischerol> createHistorischerol(@RequestBody Historischerol historischerol) throws URISyntaxException {
        log.debug("REST request to save Historischerol : {}", historischerol);
        if (historischerol.getId() != null) {
            throw new BadRequestAlertException("A new historischerol cannot already have an ID", ENTITY_NAME, "idexists");
        }
        historischerol = historischerolRepository.save(historischerol);
        return ResponseEntity.created(new URI("/api/historischerols/" + historischerol.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, historischerol.getId().toString()))
            .body(historischerol);
    }

    /**
     * {@code PUT  /historischerols/:id} : Updates an existing historischerol.
     *
     * @param id the id of the historischerol to save.
     * @param historischerol the historischerol to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated historischerol,
     * or with status {@code 400 (Bad Request)} if the historischerol is not valid,
     * or with status {@code 500 (Internal Server Error)} if the historischerol couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Historischerol> updateHistorischerol(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Historischerol historischerol
    ) throws URISyntaxException {
        log.debug("REST request to update Historischerol : {}, {}", id, historischerol);
        if (historischerol.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, historischerol.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!historischerolRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        historischerol = historischerolRepository.save(historischerol);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, historischerol.getId().toString()))
            .body(historischerol);
    }

    /**
     * {@code PATCH  /historischerols/:id} : Partial updates given fields of an existing historischerol, field will ignore if it is null
     *
     * @param id the id of the historischerol to save.
     * @param historischerol the historischerol to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated historischerol,
     * or with status {@code 400 (Bad Request)} if the historischerol is not valid,
     * or with status {@code 404 (Not Found)} if the historischerol is not found,
     * or with status {@code 500 (Internal Server Error)} if the historischerol couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Historischerol> partialUpdateHistorischerol(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Historischerol historischerol
    ) throws URISyntaxException {
        log.debug("REST request to partial update Historischerol partially : {}, {}", id, historischerol);
        if (historischerol.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, historischerol.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!historischerolRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Historischerol> result = historischerolRepository
            .findById(historischerol.getId())
            .map(existingHistorischerol -> {
                if (historischerol.getNaam() != null) {
                    existingHistorischerol.setNaam(historischerol.getNaam());
                }
                if (historischerol.getOmschrijving() != null) {
                    existingHistorischerol.setOmschrijving(historischerol.getOmschrijving());
                }

                return existingHistorischerol;
            })
            .map(historischerolRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, historischerol.getId().toString())
        );
    }

    /**
     * {@code GET  /historischerols} : get all the historischerols.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of historischerols in body.
     */
    @GetMapping("")
    public List<Historischerol> getAllHistorischerols() {
        log.debug("REST request to get all Historischerols");
        return historischerolRepository.findAll();
    }

    /**
     * {@code GET  /historischerols/:id} : get the "id" historischerol.
     *
     * @param id the id of the historischerol to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the historischerol, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Historischerol> getHistorischerol(@PathVariable("id") Long id) {
        log.debug("REST request to get Historischerol : {}", id);
        Optional<Historischerol> historischerol = historischerolRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(historischerol);
    }

    /**
     * {@code DELETE  /historischerols/:id} : delete the "id" historischerol.
     *
     * @param id the id of the historischerol to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHistorischerol(@PathVariable("id") Long id) {
        log.debug("REST request to delete Historischerol : {}", id);
        historischerolRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
