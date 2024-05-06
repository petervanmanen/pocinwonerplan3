package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Doelstelling;
import nl.ritense.demo.repository.DoelstellingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Doelstelling}.
 */
@RestController
@RequestMapping("/api/doelstellings")
@Transactional
public class DoelstellingResource {

    private final Logger log = LoggerFactory.getLogger(DoelstellingResource.class);

    private static final String ENTITY_NAME = "doelstelling";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DoelstellingRepository doelstellingRepository;

    public DoelstellingResource(DoelstellingRepository doelstellingRepository) {
        this.doelstellingRepository = doelstellingRepository;
    }

    /**
     * {@code POST  /doelstellings} : Create a new doelstelling.
     *
     * @param doelstelling the doelstelling to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new doelstelling, or with status {@code 400 (Bad Request)} if the doelstelling has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Doelstelling> createDoelstelling(@Valid @RequestBody Doelstelling doelstelling) throws URISyntaxException {
        log.debug("REST request to save Doelstelling : {}", doelstelling);
        if (doelstelling.getId() != null) {
            throw new BadRequestAlertException("A new doelstelling cannot already have an ID", ENTITY_NAME, "idexists");
        }
        doelstelling = doelstellingRepository.save(doelstelling);
        return ResponseEntity.created(new URI("/api/doelstellings/" + doelstelling.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, doelstelling.getId().toString()))
            .body(doelstelling);
    }

    /**
     * {@code PUT  /doelstellings/:id} : Updates an existing doelstelling.
     *
     * @param id the id of the doelstelling to save.
     * @param doelstelling the doelstelling to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated doelstelling,
     * or with status {@code 400 (Bad Request)} if the doelstelling is not valid,
     * or with status {@code 500 (Internal Server Error)} if the doelstelling couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Doelstelling> updateDoelstelling(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Doelstelling doelstelling
    ) throws URISyntaxException {
        log.debug("REST request to update Doelstelling : {}, {}", id, doelstelling);
        if (doelstelling.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, doelstelling.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!doelstellingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        doelstelling = doelstellingRepository.save(doelstelling);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, doelstelling.getId().toString()))
            .body(doelstelling);
    }

    /**
     * {@code PATCH  /doelstellings/:id} : Partial updates given fields of an existing doelstelling, field will ignore if it is null
     *
     * @param id the id of the doelstelling to save.
     * @param doelstelling the doelstelling to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated doelstelling,
     * or with status {@code 400 (Bad Request)} if the doelstelling is not valid,
     * or with status {@code 404 (Not Found)} if the doelstelling is not found,
     * or with status {@code 500 (Internal Server Error)} if the doelstelling couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Doelstelling> partialUpdateDoelstelling(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Doelstelling doelstelling
    ) throws URISyntaxException {
        log.debug("REST request to partial update Doelstelling partially : {}, {}", id, doelstelling);
        if (doelstelling.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, doelstelling.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!doelstellingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Doelstelling> result = doelstellingRepository
            .findById(doelstelling.getId())
            .map(existingDoelstelling -> {
                if (doelstelling.getOmschrijving() != null) {
                    existingDoelstelling.setOmschrijving(doelstelling.getOmschrijving());
                }

                return existingDoelstelling;
            })
            .map(doelstellingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, doelstelling.getId().toString())
        );
    }

    /**
     * {@code GET  /doelstellings} : get all the doelstellings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of doelstellings in body.
     */
    @GetMapping("")
    public List<Doelstelling> getAllDoelstellings() {
        log.debug("REST request to get all Doelstellings");
        return doelstellingRepository.findAll();
    }

    /**
     * {@code GET  /doelstellings/:id} : get the "id" doelstelling.
     *
     * @param id the id of the doelstelling to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the doelstelling, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Doelstelling> getDoelstelling(@PathVariable("id") Long id) {
        log.debug("REST request to get Doelstelling : {}", id);
        Optional<Doelstelling> doelstelling = doelstellingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(doelstelling);
    }

    /**
     * {@code DELETE  /doelstellings/:id} : delete the "id" doelstelling.
     *
     * @param id the id of the doelstelling to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoelstelling(@PathVariable("id") Long id) {
        log.debug("REST request to delete Doelstelling : {}", id);
        doelstellingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
