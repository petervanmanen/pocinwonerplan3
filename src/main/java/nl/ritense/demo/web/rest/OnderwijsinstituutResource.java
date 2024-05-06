package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Onderwijsinstituut;
import nl.ritense.demo.repository.OnderwijsinstituutRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Onderwijsinstituut}.
 */
@RestController
@RequestMapping("/api/onderwijsinstituuts")
@Transactional
public class OnderwijsinstituutResource {

    private final Logger log = LoggerFactory.getLogger(OnderwijsinstituutResource.class);

    private static final String ENTITY_NAME = "onderwijsinstituut";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OnderwijsinstituutRepository onderwijsinstituutRepository;

    public OnderwijsinstituutResource(OnderwijsinstituutRepository onderwijsinstituutRepository) {
        this.onderwijsinstituutRepository = onderwijsinstituutRepository;
    }

    /**
     * {@code POST  /onderwijsinstituuts} : Create a new onderwijsinstituut.
     *
     * @param onderwijsinstituut the onderwijsinstituut to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new onderwijsinstituut, or with status {@code 400 (Bad Request)} if the onderwijsinstituut has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Onderwijsinstituut> createOnderwijsinstituut(@Valid @RequestBody Onderwijsinstituut onderwijsinstituut)
        throws URISyntaxException {
        log.debug("REST request to save Onderwijsinstituut : {}", onderwijsinstituut);
        if (onderwijsinstituut.getId() != null) {
            throw new BadRequestAlertException("A new onderwijsinstituut cannot already have an ID", ENTITY_NAME, "idexists");
        }
        onderwijsinstituut = onderwijsinstituutRepository.save(onderwijsinstituut);
        return ResponseEntity.created(new URI("/api/onderwijsinstituuts/" + onderwijsinstituut.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, onderwijsinstituut.getId().toString()))
            .body(onderwijsinstituut);
    }

    /**
     * {@code PUT  /onderwijsinstituuts/:id} : Updates an existing onderwijsinstituut.
     *
     * @param id the id of the onderwijsinstituut to save.
     * @param onderwijsinstituut the onderwijsinstituut to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated onderwijsinstituut,
     * or with status {@code 400 (Bad Request)} if the onderwijsinstituut is not valid,
     * or with status {@code 500 (Internal Server Error)} if the onderwijsinstituut couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Onderwijsinstituut> updateOnderwijsinstituut(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Onderwijsinstituut onderwijsinstituut
    ) throws URISyntaxException {
        log.debug("REST request to update Onderwijsinstituut : {}, {}", id, onderwijsinstituut);
        if (onderwijsinstituut.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, onderwijsinstituut.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!onderwijsinstituutRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        onderwijsinstituut = onderwijsinstituutRepository.save(onderwijsinstituut);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, onderwijsinstituut.getId().toString()))
            .body(onderwijsinstituut);
    }

    /**
     * {@code PATCH  /onderwijsinstituuts/:id} : Partial updates given fields of an existing onderwijsinstituut, field will ignore if it is null
     *
     * @param id the id of the onderwijsinstituut to save.
     * @param onderwijsinstituut the onderwijsinstituut to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated onderwijsinstituut,
     * or with status {@code 400 (Bad Request)} if the onderwijsinstituut is not valid,
     * or with status {@code 404 (Not Found)} if the onderwijsinstituut is not found,
     * or with status {@code 500 (Internal Server Error)} if the onderwijsinstituut couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Onderwijsinstituut> partialUpdateOnderwijsinstituut(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Onderwijsinstituut onderwijsinstituut
    ) throws URISyntaxException {
        log.debug("REST request to partial update Onderwijsinstituut partially : {}, {}", id, onderwijsinstituut);
        if (onderwijsinstituut.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, onderwijsinstituut.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!onderwijsinstituutRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Onderwijsinstituut> result = onderwijsinstituutRepository
            .findById(onderwijsinstituut.getId())
            .map(onderwijsinstituutRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, onderwijsinstituut.getId().toString())
        );
    }

    /**
     * {@code GET  /onderwijsinstituuts} : get all the onderwijsinstituuts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of onderwijsinstituuts in body.
     */
    @GetMapping("")
    public List<Onderwijsinstituut> getAllOnderwijsinstituuts() {
        log.debug("REST request to get all Onderwijsinstituuts");
        return onderwijsinstituutRepository.findAll();
    }

    /**
     * {@code GET  /onderwijsinstituuts/:id} : get the "id" onderwijsinstituut.
     *
     * @param id the id of the onderwijsinstituut to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the onderwijsinstituut, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Onderwijsinstituut> getOnderwijsinstituut(@PathVariable("id") Long id) {
        log.debug("REST request to get Onderwijsinstituut : {}", id);
        Optional<Onderwijsinstituut> onderwijsinstituut = onderwijsinstituutRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(onderwijsinstituut);
    }

    /**
     * {@code DELETE  /onderwijsinstituuts/:id} : delete the "id" onderwijsinstituut.
     *
     * @param id the id of the onderwijsinstituut to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOnderwijsinstituut(@PathVariable("id") Long id) {
        log.debug("REST request to delete Onderwijsinstituut : {}", id);
        onderwijsinstituutRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
