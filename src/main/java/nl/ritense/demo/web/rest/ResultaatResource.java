package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import nl.ritense.demo.domain.Resultaat;
import nl.ritense.demo.repository.ResultaatRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Resultaat}.
 */
@RestController
@RequestMapping("/api/resultaats")
@Transactional
public class ResultaatResource {

    private final Logger log = LoggerFactory.getLogger(ResultaatResource.class);

    private static final String ENTITY_NAME = "resultaat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResultaatRepository resultaatRepository;

    public ResultaatResource(ResultaatRepository resultaatRepository) {
        this.resultaatRepository = resultaatRepository;
    }

    /**
     * {@code POST  /resultaats} : Create a new resultaat.
     *
     * @param resultaat the resultaat to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new resultaat, or with status {@code 400 (Bad Request)} if the resultaat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Resultaat> createResultaat(@RequestBody Resultaat resultaat) throws URISyntaxException {
        log.debug("REST request to save Resultaat : {}", resultaat);
        if (resultaat.getId() != null) {
            throw new BadRequestAlertException("A new resultaat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        resultaat = resultaatRepository.save(resultaat);
        return ResponseEntity.created(new URI("/api/resultaats/" + resultaat.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, resultaat.getId().toString()))
            .body(resultaat);
    }

    /**
     * {@code PUT  /resultaats/:id} : Updates an existing resultaat.
     *
     * @param id the id of the resultaat to save.
     * @param resultaat the resultaat to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resultaat,
     * or with status {@code 400 (Bad Request)} if the resultaat is not valid,
     * or with status {@code 500 (Internal Server Error)} if the resultaat couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Resultaat> updateResultaat(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Resultaat resultaat
    ) throws URISyntaxException {
        log.debug("REST request to update Resultaat : {}, {}", id, resultaat);
        if (resultaat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, resultaat.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!resultaatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        resultaat = resultaatRepository.save(resultaat);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, resultaat.getId().toString()))
            .body(resultaat);
    }

    /**
     * {@code PATCH  /resultaats/:id} : Partial updates given fields of an existing resultaat, field will ignore if it is null
     *
     * @param id the id of the resultaat to save.
     * @param resultaat the resultaat to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resultaat,
     * or with status {@code 400 (Bad Request)} if the resultaat is not valid,
     * or with status {@code 404 (Not Found)} if the resultaat is not found,
     * or with status {@code 500 (Internal Server Error)} if the resultaat couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Resultaat> partialUpdateResultaat(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Resultaat resultaat
    ) throws URISyntaxException {
        log.debug("REST request to partial update Resultaat partially : {}, {}", id, resultaat);
        if (resultaat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, resultaat.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!resultaatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Resultaat> result = resultaatRepository
            .findById(resultaat.getId())
            .map(existingResultaat -> {
                if (resultaat.getDatum() != null) {
                    existingResultaat.setDatum(resultaat.getDatum());
                }
                if (resultaat.getNaam() != null) {
                    existingResultaat.setNaam(resultaat.getNaam());
                }
                if (resultaat.getOmschrijving() != null) {
                    existingResultaat.setOmschrijving(resultaat.getOmschrijving());
                }

                return existingResultaat;
            })
            .map(resultaatRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, resultaat.getId().toString())
        );
    }

    /**
     * {@code GET  /resultaats} : get all the resultaats.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of resultaats in body.
     */
    @GetMapping("")
    public List<Resultaat> getAllResultaats(@RequestParam(name = "filter", required = false) String filter) {
        if ("heeftresultaatproject-is-null".equals(filter)) {
            log.debug("REST request to get all Resultaats where heeftresultaatProject is null");
            return StreamSupport.stream(resultaatRepository.findAll().spliterator(), false)
                .filter(resultaat -> resultaat.getHeeftresultaatProject() == null)
                .toList();
        }

        if ("heeftresultaattraject-is-null".equals(filter)) {
            log.debug("REST request to get all Resultaats where heeftresultaatTraject is null");
            return StreamSupport.stream(resultaatRepository.findAll().spliterator(), false)
                .filter(resultaat -> resultaat.getHeeftresultaatTraject() == null)
                .toList();
        }
        log.debug("REST request to get all Resultaats");
        return resultaatRepository.findAll();
    }

    /**
     * {@code GET  /resultaats/:id} : get the "id" resultaat.
     *
     * @param id the id of the resultaat to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the resultaat, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Resultaat> getResultaat(@PathVariable("id") Long id) {
        log.debug("REST request to get Resultaat : {}", id);
        Optional<Resultaat> resultaat = resultaatRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(resultaat);
    }

    /**
     * {@code DELETE  /resultaats/:id} : delete the "id" resultaat.
     *
     * @param id the id of the resultaat to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResultaat(@PathVariable("id") Long id) {
        log.debug("REST request to delete Resultaat : {}", id);
        resultaatRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
