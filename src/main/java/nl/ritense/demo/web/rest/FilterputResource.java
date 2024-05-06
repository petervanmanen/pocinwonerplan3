package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Filterput;
import nl.ritense.demo.repository.FilterputRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Filterput}.
 */
@RestController
@RequestMapping("/api/filterputs")
@Transactional
public class FilterputResource {

    private final Logger log = LoggerFactory.getLogger(FilterputResource.class);

    private static final String ENTITY_NAME = "filterput";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FilterputRepository filterputRepository;

    public FilterputResource(FilterputRepository filterputRepository) {
        this.filterputRepository = filterputRepository;
    }

    /**
     * {@code POST  /filterputs} : Create a new filterput.
     *
     * @param filterput the filterput to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new filterput, or with status {@code 400 (Bad Request)} if the filterput has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Filterput> createFilterput(@RequestBody Filterput filterput) throws URISyntaxException {
        log.debug("REST request to save Filterput : {}", filterput);
        if (filterput.getId() != null) {
            throw new BadRequestAlertException("A new filterput cannot already have an ID", ENTITY_NAME, "idexists");
        }
        filterput = filterputRepository.save(filterput);
        return ResponseEntity.created(new URI("/api/filterputs/" + filterput.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, filterput.getId().toString()))
            .body(filterput);
    }

    /**
     * {@code PUT  /filterputs/:id} : Updates an existing filterput.
     *
     * @param id the id of the filterput to save.
     * @param filterput the filterput to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated filterput,
     * or with status {@code 400 (Bad Request)} if the filterput is not valid,
     * or with status {@code 500 (Internal Server Error)} if the filterput couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Filterput> updateFilterput(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Filterput filterput
    ) throws URISyntaxException {
        log.debug("REST request to update Filterput : {}, {}", id, filterput);
        if (filterput.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, filterput.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!filterputRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        filterput = filterputRepository.save(filterput);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, filterput.getId().toString()))
            .body(filterput);
    }

    /**
     * {@code PATCH  /filterputs/:id} : Partial updates given fields of an existing filterput, field will ignore if it is null
     *
     * @param id the id of the filterput to save.
     * @param filterput the filterput to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated filterput,
     * or with status {@code 400 (Bad Request)} if the filterput is not valid,
     * or with status {@code 404 (Not Found)} if the filterput is not found,
     * or with status {@code 500 (Internal Server Error)} if the filterput couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Filterput> partialUpdateFilterput(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Filterput filterput
    ) throws URISyntaxException {
        log.debug("REST request to partial update Filterput partially : {}, {}", id, filterput);
        if (filterput.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, filterput.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!filterputRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Filterput> result = filterputRepository
            .findById(filterput.getId())
            .map(existingFilterput -> {
                if (filterput.getDrain() != null) {
                    existingFilterput.setDrain(filterput.getDrain());
                }
                if (filterput.getRisicogebied() != null) {
                    existingFilterput.setRisicogebied(filterput.getRisicogebied());
                }

                return existingFilterput;
            })
            .map(filterputRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, filterput.getId().toString())
        );
    }

    /**
     * {@code GET  /filterputs} : get all the filterputs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of filterputs in body.
     */
    @GetMapping("")
    public List<Filterput> getAllFilterputs() {
        log.debug("REST request to get all Filterputs");
        return filterputRepository.findAll();
    }

    /**
     * {@code GET  /filterputs/:id} : get the "id" filterput.
     *
     * @param id the id of the filterput to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the filterput, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Filterput> getFilterput(@PathVariable("id") Long id) {
        log.debug("REST request to get Filterput : {}", id);
        Optional<Filterput> filterput = filterputRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(filterput);
    }

    /**
     * {@code DELETE  /filterputs/:id} : delete the "id" filterput.
     *
     * @param id the id of the filterput to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFilterput(@PathVariable("id") Long id) {
        log.debug("REST request to delete Filterput : {}", id);
        filterputRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
