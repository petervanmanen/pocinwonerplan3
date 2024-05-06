package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Individueelkeuzebudget;
import nl.ritense.demo.repository.IndividueelkeuzebudgetRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Individueelkeuzebudget}.
 */
@RestController
@RequestMapping("/api/individueelkeuzebudgets")
@Transactional
public class IndividueelkeuzebudgetResource {

    private final Logger log = LoggerFactory.getLogger(IndividueelkeuzebudgetResource.class);

    private static final String ENTITY_NAME = "individueelkeuzebudget";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IndividueelkeuzebudgetRepository individueelkeuzebudgetRepository;

    public IndividueelkeuzebudgetResource(IndividueelkeuzebudgetRepository individueelkeuzebudgetRepository) {
        this.individueelkeuzebudgetRepository = individueelkeuzebudgetRepository;
    }

    /**
     * {@code POST  /individueelkeuzebudgets} : Create a new individueelkeuzebudget.
     *
     * @param individueelkeuzebudget the individueelkeuzebudget to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new individueelkeuzebudget, or with status {@code 400 (Bad Request)} if the individueelkeuzebudget has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Individueelkeuzebudget> createIndividueelkeuzebudget(@RequestBody Individueelkeuzebudget individueelkeuzebudget)
        throws URISyntaxException {
        log.debug("REST request to save Individueelkeuzebudget : {}", individueelkeuzebudget);
        if (individueelkeuzebudget.getId() != null) {
            throw new BadRequestAlertException("A new individueelkeuzebudget cannot already have an ID", ENTITY_NAME, "idexists");
        }
        individueelkeuzebudget = individueelkeuzebudgetRepository.save(individueelkeuzebudget);
        return ResponseEntity.created(new URI("/api/individueelkeuzebudgets/" + individueelkeuzebudget.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, individueelkeuzebudget.getId().toString()))
            .body(individueelkeuzebudget);
    }

    /**
     * {@code PUT  /individueelkeuzebudgets/:id} : Updates an existing individueelkeuzebudget.
     *
     * @param id the id of the individueelkeuzebudget to save.
     * @param individueelkeuzebudget the individueelkeuzebudget to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated individueelkeuzebudget,
     * or with status {@code 400 (Bad Request)} if the individueelkeuzebudget is not valid,
     * or with status {@code 500 (Internal Server Error)} if the individueelkeuzebudget couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Individueelkeuzebudget> updateIndividueelkeuzebudget(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Individueelkeuzebudget individueelkeuzebudget
    ) throws URISyntaxException {
        log.debug("REST request to update Individueelkeuzebudget : {}, {}", id, individueelkeuzebudget);
        if (individueelkeuzebudget.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, individueelkeuzebudget.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!individueelkeuzebudgetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        individueelkeuzebudget = individueelkeuzebudgetRepository.save(individueelkeuzebudget);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, individueelkeuzebudget.getId().toString()))
            .body(individueelkeuzebudget);
    }

    /**
     * {@code PATCH  /individueelkeuzebudgets/:id} : Partial updates given fields of an existing individueelkeuzebudget, field will ignore if it is null
     *
     * @param id the id of the individueelkeuzebudget to save.
     * @param individueelkeuzebudget the individueelkeuzebudget to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated individueelkeuzebudget,
     * or with status {@code 400 (Bad Request)} if the individueelkeuzebudget is not valid,
     * or with status {@code 404 (Not Found)} if the individueelkeuzebudget is not found,
     * or with status {@code 500 (Internal Server Error)} if the individueelkeuzebudget couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Individueelkeuzebudget> partialUpdateIndividueelkeuzebudget(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Individueelkeuzebudget individueelkeuzebudget
    ) throws URISyntaxException {
        log.debug("REST request to partial update Individueelkeuzebudget partially : {}, {}", id, individueelkeuzebudget);
        if (individueelkeuzebudget.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, individueelkeuzebudget.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!individueelkeuzebudgetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Individueelkeuzebudget> result = individueelkeuzebudgetRepository
            .findById(individueelkeuzebudget.getId())
            .map(existingIndividueelkeuzebudget -> {
                if (individueelkeuzebudget.getBedrag() != null) {
                    existingIndividueelkeuzebudget.setBedrag(individueelkeuzebudget.getBedrag());
                }
                if (individueelkeuzebudget.getDatumeinde() != null) {
                    existingIndividueelkeuzebudget.setDatumeinde(individueelkeuzebudget.getDatumeinde());
                }
                if (individueelkeuzebudget.getDatumstart() != null) {
                    existingIndividueelkeuzebudget.setDatumstart(individueelkeuzebudget.getDatumstart());
                }
                if (individueelkeuzebudget.getDatumtoekenning() != null) {
                    existingIndividueelkeuzebudget.setDatumtoekenning(individueelkeuzebudget.getDatumtoekenning());
                }

                return existingIndividueelkeuzebudget;
            })
            .map(individueelkeuzebudgetRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, individueelkeuzebudget.getId().toString())
        );
    }

    /**
     * {@code GET  /individueelkeuzebudgets} : get all the individueelkeuzebudgets.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of individueelkeuzebudgets in body.
     */
    @GetMapping("")
    public List<Individueelkeuzebudget> getAllIndividueelkeuzebudgets() {
        log.debug("REST request to get all Individueelkeuzebudgets");
        return individueelkeuzebudgetRepository.findAll();
    }

    /**
     * {@code GET  /individueelkeuzebudgets/:id} : get the "id" individueelkeuzebudget.
     *
     * @param id the id of the individueelkeuzebudget to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the individueelkeuzebudget, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Individueelkeuzebudget> getIndividueelkeuzebudget(@PathVariable("id") Long id) {
        log.debug("REST request to get Individueelkeuzebudget : {}", id);
        Optional<Individueelkeuzebudget> individueelkeuzebudget = individueelkeuzebudgetRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(individueelkeuzebudget);
    }

    /**
     * {@code DELETE  /individueelkeuzebudgets/:id} : delete the "id" individueelkeuzebudget.
     *
     * @param id the id of the individueelkeuzebudget to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIndividueelkeuzebudget(@PathVariable("id") Long id) {
        log.debug("REST request to delete Individueelkeuzebudget : {}", id);
        individueelkeuzebudgetRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
